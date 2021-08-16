package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skill.project.dto.response.PostResponse;
import skill.project.model.enums.ModeType;
import skill.project.service.PostService;

/*Для запросов на post/... */
@RestController
@RequestMapping("/api/post")
public class PostController {
  @Autowired
  private PostService postService;

  @GetMapping
  public ResponseEntity<?> getPost(@RequestParam(name = "mode", defaultValue = "recent") ModeType mode,
                                   @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                   @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit) {
    return ResponseEntity.ok(postService.getPosts(mode, offset, limit));
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchPost(@RequestParam(name = "query", required = false) String query,
                                      @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                      @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit) {
    return new ResponseEntity<>(postService.searchPost(query, offset, limit), HttpStatus.OK);
  }

  @GetMapping("/byDate")
  public ResponseEntity<?> getPostByDate(String date,
                                         @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                         @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit) {
    return new ResponseEntity<>(postService.getPostByDate(date, offset, limit), HttpStatus.OK);
  }

  @GetMapping("/byTag")
  public ResponseEntity<?> getPostByTag(String tag,
                                        @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                        @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit) {
    return new ResponseEntity<>(postService.getPostByTag(tag, offset, limit), HttpStatus.OK);
  }

  @GetMapping("/{ID}")
  public ResponseEntity<?> getPostId(@PathVariable(name = "ID") Integer postId) {
    return new ResponseEntity<>(postService.getPostId(postId), HttpStatus.OK);
  }
}
