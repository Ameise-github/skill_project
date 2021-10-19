package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skill.project.dto.request.RegisterRequest;
import skill.project.dto.response.AuthCheckResponse;
import skill.project.service.CaptchaService;
import skill.project.service.RegistrationService;

/*Для запросов на auth/... */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private CaptchaService captchaService;
  @Autowired
  private RegistrationService registrationService;

  @GetMapping("/check")
  public ResponseEntity<?> authCheck() {
    return ResponseEntity.ok(AuthCheckResponse.builder().result(false).build());
  }

  @GetMapping("/captcha")
  public ResponseEntity<?> getCaptcha() {
    return new ResponseEntity<>(captchaService.generateCaptcha(), HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest register) {
    return new ResponseEntity<>(registrationService.register(register), HttpStatus.OK);
  }
}
