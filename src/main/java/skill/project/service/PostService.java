package skill.project.service;

import skill.project.dto.PostDto;
import skill.project.dto.response.PostResponse;
import skill.project.model.enums.ModeType;


public interface PostService {
  PostResponse getPosts(ModeType mode, Integer offset, Integer limit);

  PostResponse searchPost(String query, Integer offset, Integer limit);

  PostResponse getPostByDate(String date, Integer offset, Integer limit);

  PostResponse getPostByTag(String tag, Integer offset, Integer limit);

  PostDto getPostId(Integer postId);
}
