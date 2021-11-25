package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skill.project.dto.request.CommentRequest;
import skill.project.security.CustomUser;
import skill.project.security.UserPrincipal;
import skill.project.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
  @Autowired
  private CommentService commentService;


  @PostMapping("")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> addComment(@UserPrincipal CustomUser principal, @RequestBody CommentRequest newComment) {
    return ResponseEntity.ok(commentService.addComment(principal.getId(), newComment));
  }
}
