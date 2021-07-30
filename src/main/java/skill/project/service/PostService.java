package skill.project.service;

import skill.project.dto.response.PostResponse;
import skill.project.model.enums.ModeType;


public interface PostService {
  PostResponse getPosts(ModeType mode, Integer offset, Integer limit);
}
