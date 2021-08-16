package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skill.project.dto.response.AuthCheckResponse;
import skill.project.service.CaptchaService;

/*Для запросов на auth/... */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private CaptchaService captchaService;

  @GetMapping("/check")
  public ResponseEntity<?> authCheck() {
    return ResponseEntity.ok(AuthCheckResponse.builder().result(false).build());
  }

  @GetMapping("/captcha")
  public ResponseEntity<?> getCaptcha() {
      captchaService.generateCaptcha();
      return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
