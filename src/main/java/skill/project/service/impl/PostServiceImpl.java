package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import skill.project.dto.CommentDto;
import skill.project.dto.PostDto;
import skill.project.dto.response.PostResponse;
import skill.project.exeption.NotFoundException;
import skill.project.model.Post;
import skill.project.model.PostComments;
import skill.project.model.SocialInfo;
import skill.project.model.enums.ModeType;
import skill.project.model.enums.ModeratorEnum;
import skill.project.repository.CommentRepository;
import skill.project.repository.PostRepository;
import skill.project.repository.SocialInfoRepository;
import skill.project.service.PostService;
import skill.project.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final SocialInfoRepository socialInfoRepository;
  private final CommentRepository commentRepository;

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
  public PostDto getPostId(Integer postId) {
    //TODO исправить возврат ошибки
    Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Пост не найден", HttpStatus.NOT_FOUND));
    if (!post.isActive() || post.getModerationStatus() != ModeratorEnum.ACCEPTED || post.getTimeCreate().isAfter(LocalDateTime.now())) {
      throw new NotFoundException("Пост не найден", HttpStatus.NOT_FOUND);
    }
    List<SocialInfo> socialInfos = socialInfoRepository.getAllPosts(Collections.singletonList(postId));
    PostDto postDto = PostDto.getFullPostDto(post, socialInfos.get(0));
    List<PostComments> comments = commentRepository.getPostCommentsByPost(post);
    postDto.setComments(comments.stream().map(CommentDto::new).collect(Collectors.toList()));
    //TODO прописать логику когда появится авторизация
//    if (post.getUser().getId() != currentUser.getId() && !currentUser.IsModerator())
    post.setViewCount(post.getViewCount() + 1);
    postRepository.save(post);
    return postDto;
  }

  private List<PostDto> getPostDto(List<Post> posts) {
    List<SocialInfo> allSocial = socialInfoRepository.getAllPosts(posts.stream().map(Post::getId).collect(Collectors.toList()));
    Map<Integer, SocialInfo> infoMap = allSocial.stream().collect(Collectors.toMap(SocialInfo::getObjId, s -> s));
    return posts.stream().map(p -> new PostDto(p, infoMap.get(p.getId()))).collect(Collectors.toList());
  }
}
