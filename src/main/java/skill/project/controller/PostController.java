package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skill.project.dto.request.LikeRequest;
import skill.project.dto.request.PostRequest;
import skill.project.dto.response.Response;
import skill.project.model.enums.ModeType;
import skill.project.security.CustomUser;
import skill.project.security.UserPrincipal;
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
  public ResponseEntity<?> getPostId(@PathVariable(name = "ID") Integer postId, @UserPrincipal CustomUser principal) {
    return new ResponseEntity<>(postService.getPostId(postId, principal), HttpStatus.OK);
  }

  @GetMapping("/my")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> getMyPost(@RequestParam(name = "status") String  status,
                                     @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                     @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                                     @UserPrincipal CustomUser principal){
    return ResponseEntity.ok(postService.getMyPosts(status, offset, limit, principal));
  }

  @GetMapping("/moderation")
  @PreAuthorize("hasAuthority('user:moderator')")
  public ResponseEntity<?> getPostModeration(@RequestParam(name = "status") String  status,
                                             @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                             @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                                             @UserPrincipal CustomUser principal) {
    return ResponseEntity.ok(postService.getPostsModeration(status, offset, limit, principal.getId()));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> addPost(@RequestBody PostRequest postRequest, @UserPrincipal CustomUser principal) {
    return new ResponseEntity<>(postService.addPost(postRequest, principal.getId()), HttpStatus.OK);
  }

  @PutMapping("/{ID}")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> editPost(@PathVariable(name = "ID") Integer postId,
                                    @RequestBody PostRequest postRequest,
                                    @UserPrincipal CustomUser principal) {
    return new ResponseEntity<>(postService.editPost(postId, postRequest, principal), HttpStatus.OK);
  }

  @PostMapping("/like")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> setLke(@UserPrincipal CustomUser principal, @RequestBody LikeRequest newLike) {
    newLike.setValue(1);
    return new ResponseEntity<>(postService.setLike(principal.getId(), newLike), HttpStatus.OK);
  }

  @PostMapping("/dislike")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> setDislike(@UserPrincipal CustomUser principal, @RequestBody LikeRequest newDislike) {
    newDislike.setValue(-1);
    return new ResponseEntity<>(postService.setLike(principal.getId(), newDislike), HttpStatus.OK);
  }
}
