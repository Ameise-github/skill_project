package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import skill.project.dto.PostDto;
import skill.project.dto.response.PostResponse;
import skill.project.model.Post;
import skill.project.model.SocialInfo;
import skill.project.model.enums.ModeType;
import skill.project.repository.PostRepository;
import skill.project.repository.SocialInfoRepository;
import skill.project.service.PostService;
import skill.project.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final SocialInfoRepository socialInfoRepository;

  @Override
  public PostResponse getPosts(ModeType mode, Integer offset, Integer limit) {
    PostResponse posts = new PostResponse();
    Page<Post> page = postRepository.getPosts(mode.name(), Utils.getPageable(offset, limit));
    List<Post> postsL = page.getContent();
    List<SocialInfo> allSocial = socialInfoRepository.getAllPosts(postsL.stream().map(Post::getId).collect(Collectors.toList()));
    Map<Integer, SocialInfo> infoMap = allSocial.stream().collect(Collectors.toMap(SocialInfo::getObjId, s -> s));
    posts.setCount(page.getTotalElements());
    posts.setPosts(postsL.stream().map(p -> new PostDto(p, infoMap.get(p.getId()))).collect(Collectors.toList()));
    return posts;
  }

  private String getSort(ModeType mode) {
    String sort;
    switch (mode) {
      case recent:
        sort = "t.time desc";
        break;
      case popular:
        sort = "t.c_pc desc";
        break;
      case best:
        sort = "t.c_l desc";
        break;
      case early:
        sort = "t.time asc";
        break;
      default:
        sort = "t.time desc";
    }
    return sort;
  }
}
