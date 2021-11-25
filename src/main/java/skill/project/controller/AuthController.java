package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skill.project.dto.request.RegisterRequest;
import skill.project.dto.response.AuthResponse;
import skill.project.dto.request.LoginRequest;
import skill.project.security.CustomUser;
import skill.project.security.UserPrincipal;
import skill.project.service.CaptchaService;
import skill.project.service.LoginService;
import skill.project.service.ProfileService;


/*Для запросов на auth/... */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private CaptchaService captchaService;
  @Autowired
  private ProfileService profileService;
  @Autowired
  private LoginService loginService;

  @GetMapping("/check")
  public ResponseEntity<?> authCheck(@UserPrincipal CustomUser principal) {
    return ResponseEntity.ok(loginService.authCheck(principal));
  }

  @GetMapping("/captcha")
  public ResponseEntity<?> getCaptcha() {
    return new ResponseEntity<>(captchaService.generateCaptcha(), HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest register) {
    return new ResponseEntity<>(profileService.register(register), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login (@RequestBody LoginRequest user) {
    return ResponseEntity.ok().body(loginService.login(user));
  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout() {
    return ResponseEntity.ok(AuthResponse.builder().result(true).build());
  }

  @PostMapping("/restore")
  public ResponseEntity<?> restorePassword() {
    //TODO do
    return ResponseEntity.ok(null);
  }

  @PostMapping("/password")
  public ResponseEntity<?> editPassword() {
    //TODO do
    return ResponseEntity.ok(null);
  }


}
