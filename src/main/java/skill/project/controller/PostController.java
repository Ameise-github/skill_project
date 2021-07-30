package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skill.project.dto.response.PostResponse;
import skill.project.model.enums.ModeType;
import skill.project.service.PostService;

/*Для запросов на post/... */
@RestController
@RequestMapping("/post")
public class PostController {
  @Autowired
  private PostService postService;

  @GetMapping
  public ResponseEntity<?> getPost(@RequestParam(name = "mode", defaultValue = "recent") ModeType mode,
                                   @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                   @RequestParam(name = "offset", defaultValue = "10", required = false) Integer limit) {
    PostResponse posts = postService.getPosts(mode, offset, limit);
    return ResponseEntity.ok(posts);
  }


}
