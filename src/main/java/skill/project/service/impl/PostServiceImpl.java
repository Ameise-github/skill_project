package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import skill.project.dto.CommentDto;
import skill.project.dto.PostDto;
import skill.project.dto.error.PostError;
import skill.project.dto.request.LikeRequest;
import skill.project.dto.request.PostRequest;
import skill.project.dto.response.PostResponse;
import skill.project.dto.response.Response;
import skill.project.exeption.NotFoundException;
import skill.project.model.*;
import skill.project.model.enums.ModeType;
import skill.project.model.enums.ModeratorEnum;
import skill.project.repository.*;
import skill.project.security.CustomUser;
import skill.project.service.PostService;
import skill.project.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final PostVotesRepository postVotesRepository;

  @Override
  public PostResponse getPosts(ModeType mode, Integer offset, Integer limit) {
    Page<Post> page = postRepository.getPosts(mode.name(), Utils.getPageable(offset, limit));
    return new PostResponse(page.getTotalElements(), getPostDto(page.getContent()));
  }

  @Override
  public PostResponse searchPost(String query, Integer offset, Integer limit) {
    PostResponse posts;
    if (query == null || query.isEmpty() || query.trim().isEmpty()) {
      posts = getPosts(ModeType.recent, offset, limit);
    }else {
      Page<Post> postsPage = postRepository.searchPost(query, Utils.getPageable(offset, limit, Sort.by(Sort.Direction.DESC, "timeCreate")));
      posts = new PostResponse(postsPage.getTotalElements(), getPostDto(postsPage.getContent()));
    }
    return posts;
  }

  @Override
  public PostResponse getPostByDate(String dateStr, Integer offset, Integer limit) {
    LocalDate date = LocalDate.parse(dateStr);
    Page<Post> postPage = postRepository.getPostByTimeCreate(date, Utils.getPageable(offset, limit));
    return new PostResponse(postPage.getTotalElements(), getPostDto(postPage.getContent()));
  }

  @Override
  public PostResponse getPostByTag(String tag, Integer offset, Integer limit) {
    Page<Post> page = postRepository.getPostByTag(tag, Utils.getPageable(offset, limit));
    return new PostResponse(page.getTotalElements(), getPostDto(page.getContent()));
  }

  @Override
  public PostDto getPostId(Integer postId, CustomUser principal) {
    //TODO исправить возврат ошибки
    Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Пост не найден", HttpStatus.NOT_FOUND));
    if (
        (post.isActive() && post.getModerationStatus().equals(ModeratorEnum.ACCEPTED) && post.getTimeCreate().isBefore(LocalDateTime.now()))
        || (principal != null && Objects.equals(post.getUser().getId(), principal.getId()))
        || (principal != null && principal.isModeration())
    ) {
      List<SocialInfo> socialList = postRepository.getSocial(Collections.singletonList(postId));
      PostDto postDto = PostDto.getFullPostDto(post, socialList.get(0));
      List<PostComments> comments = commentRepository.getPostCommentsByPost(post);
      postDto.setComments(comments.stream().map(CommentDto::new).collect(Collectors.toList()));
      if (principal == null ||(!post.getUser().getId().equals(principal.getId()) && !principal.isModeration())) {
        post.setViewCount(post.getViewCount() == null ? 1 : post.getViewCount() + 1);
        postRepository.save(post);
      }
      return postDto;
    } else {
      throw new NotFoundException("Пост не найден", HttpStatus.NOT_FOUND);
    }
  }

  private List<PostDto> getPostDto(List<Post> posts) {
    if(posts.size() > 0) {
      List<SocialInfo> socialList = postRepository.getSocial(posts.stream().map(Post::getId).collect(Collectors.toList()));
      Map<Integer, SocialInfo> infoMap = socialList.stream().collect(Collectors.toMap(SocialInfo::getObjId, s -> s));
      return posts.stream().map(p -> new PostDto(p, infoMap.get(p.getId()))).collect(Collectors.toList());
    }else
      return new ArrayList<>();
  }

  @Override
  public PostResponse getMyPosts(String status, Integer offset, Integer limit, CustomUser principal) {
    Page<Post> postPage = postRepository.getMyPost(principal.getId(), status, Utils.getPageable(offset, limit));
    return new PostResponse(postPage.getTotalElements(), getPostDto(postPage.getContent()));
  }

  @Override
  public PostResponse getPostsModeration(String status, Integer offset, Integer limit, Integer userId) {
    Page<Post> postsPage;
    if ("new".equals(status)) {
      postsPage = postRepository.getPostsModeration(status.toUpperCase(), null, Utils.getPageable(offset, limit));
    } else {
      postsPage = postRepository.getPostsModeration(status.toUpperCase(), userId, Utils.getPageable(offset, limit));
    }
    return new PostResponse(postsPage.getTotalElements(), getPostDto(postsPage.getContent()));
  }

  @Override
  public Response addPost(PostRequest postRequest, Integer userId) {
    PostError error = validPost(postRequest);
    if (!error.isEmpty())
      return new Response(false, error);

    Post postModel = new Post();
    editedPost(postModel, postRequest, false);
    postModel.setUser(userRepository.getById(userId));
    postRepository.save(postModel);

    return new Response(true);
  }

  @Override
  public Response editPost(Integer postId, PostRequest postRequest, CustomUser principal) {
    Post postModel = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Пост не найден!"));
    PostError error = validPost(postRequest);
    if (!error.isEmpty())
      return new Response(false, error);

    editedPost(postModel, postRequest, principal.isModeration());
    postRepository.save(postModel);

    return new Response(true);
  }

  private void editedPost(Post postModel, PostRequest postRequest, boolean moderator) {
    List<Tag> tagsModel = tagRepository.findAllByName(postRequest.getTags());
    LocalDateTime dateCreate = Utils.getDateTime(postRequest.getTimestamp());

    postModel.setText(postRequest.getText());
    postModel.setTitle(postRequest.getTitle());
    postModel.setActive(postRequest.isActive());
    if (!moderator)
      postModel.setModerationStatus(ModeratorEnum.NEW);
    postModel.setTags(tagsModel);
    postModel.setTimeCreate(dateCreate.isBefore(LocalDateTime.now()) ? LocalDateTime.now() : dateCreate);
  }

  private PostError validPost(PostRequest postRequest) {
    PostError error = new PostError();
    if (postRequest.getTitle() == null || postRequest.getTitle().isEmpty() || postRequest.getTitle().length() < 3)
      error.setTitle("Заголовок слишком короткий");
    if (postRequest.getText() == null || postRequest.getText().isEmpty() || postRequest.getText().length() < 50)
      error.setText("Текст публикации слишком короткий");
    return error;
  }

  @Override
  public Response setLike(Integer userId, LikeRequest newLike) {
    Post post = postRepository.findById(newLike.getPostId()).orElseThrow(() -> new NotFoundException("Пост не найден", HttpStatus.NOT_FOUND));
    PostVotes pv = postVotesRepository.getByUser_IdAndPost_Id(userId, newLike.getPostId());
    Response res = new Response(false);
    if (pv == null) {
      pv = new PostVotes();
      pv.setPost(post);
      pv.setUser(userRepository.getById(userId));
      pv.setTimeCreate(LocalDateTime.now());
      pv.setValue(newLike.getValue());
    } else if (pv.getValue() != newLike.getValue()) {
      pv.setValue(newLike.getValue());
      pv.setTimeCreate(LocalDateTime.now());
    }else {
      return res;
    }
    postVotesRepository.save(pv);
    res.setResult(true);

    return res;
  }
}
