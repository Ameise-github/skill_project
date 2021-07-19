package skill.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skill.project.dto.response.AuthCheckResponse;

@RestController
public class AuthController {
@RequestMapping("/auth")
public class ApiAuthController {

  @GetMapping("/check")
  public ResponseEntity<?> authCheck() {

    return ResponseEntity.ok(AuthCheckResponse.builder().result(false).build());
  }
}
