package skill.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
  @GetMapping("/init")
  public ResponseEntity<?> getInit() {

    return ResponseEntity.ok().body("");
  }

  @GetMapping("/settings")
  public ResponseEntity<?> getSettings() {

    return ResponseEntity.ok().body("");
  }

}
