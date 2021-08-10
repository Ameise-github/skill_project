package skill.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skill.project.dto.response.AuthCheckResponse;

/*Для запросов на auth/... */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @GetMapping("/check")
  public ResponseEntity<?> authCheck() {
    return ResponseEntity.ok(AuthCheckResponse.builder().result(false).build());
  }
}
