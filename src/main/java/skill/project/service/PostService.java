package skill.project.service;

import skill.project.dto.response.PostResponse;
import skill.project.enums.ModeType;

import java.util.List;

public interface PostService {
  List<PostResponse> getPosts(ModeType mode, Integer offset, Integer limit);
}
