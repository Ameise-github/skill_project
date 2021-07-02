package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skill.project.dto.response.PostResponse;
import skill.project.enums.ModeType;
import skill.project.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  @Override
  public List<PostResponse> getPosts(ModeType mode, Integer offset, Integer limit) {
    List<PostResponse> posts = new ArrayList<>();


    return posts;
  }
}
