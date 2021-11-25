package skill.project.service;

import skill.project.dto.PostDto;
import skill.project.dto.request.PostRequest;
import skill.project.dto.response.PostResponse;
import skill.project.dto.response.Response;
import skill.project.model.enums.ModeType;
import skill.project.security.CustomUser;


public interface PostService {
  PostResponse getPosts(ModeType mode, Integer offset, Integer limit);

  PostResponse searchPost(String query, Integer offset, Integer limit);

  PostResponse getPostByDate(String dateStr, Integer offset, Integer limit);

  PostResponse getPostByTag(String tag, Integer offset, Integer limit);

  PostDto getPostId(Integer postId, CustomUser principal);

  PostResponse getMyPosts(String status, Integer offset, Integer limit, CustomUser principal);

  PostResponse getPostsModeration(String status, Integer offset, Integer limit, Integer userId);

  Response addPost(PostRequest postRequest, Integer userId);

  Response editPost(Integer postId, PostRequest postRequest, CustomUser principal);
}
