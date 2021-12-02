package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import skill.project.dto.response.AuthResponse;
import skill.project.dto.request.LoginRequest;
import skill.project.dto.response.Response;
import skill.project.dto.response.UserResponse;
import skill.project.email.EmailService;
import skill.project.email.Mail;
import skill.project.model.User;
import skill.project.repository.PostRepository;
import skill.project.repository.UserRepository;
import skill.project.security.CustomUser;
import skill.project.service.LoginService;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
  private final AuthenticationManager authenticationManager;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final EmailService emailService;


  @Override
  public AuthResponse authCheck(CustomUser principal) {
    if (principal == null)
      return new AuthResponse();

    AuthResponse response = AuthResponse.builder()
        .result(true)
        .user(new UserResponse(principal))
        .build();
    if (principal.isModeration()) {
      response.getUser().setModerationCount(postRepository.countPostsForModeration(principal.getId()));
    }
    return response;
  }

  @Override
  public AuthResponse login(LoginRequest request) {
    Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(auth);
    CustomUser principal = (CustomUser) auth.getPrincipal();
    AuthResponse response = AuthResponse.builder()
        .result(true)
        .user(new UserResponse(principal))
        .build();
    if (principal.isModeration()) {
      response.getUser().setModerationCount(postRepository.countPostsForModeration(principal.getId()));
    }
    return response;
  }

  @Override
  public Response restorePassword(Map<String, String> body, String host) {
    String email = body.get("email");
    User user = userRepository.findByEmailLike(email);
    if (user == null) {
      return new Response(false);
    }

    String token = UUID.randomUUID().toString();
    user.setCode(token);
    userRepository.save(user);

    //отправить письмо
    String url = host + "/login/change-password/" + token;
    emailService.sendRestoreMessage(new Mail("blogAlex@gmail.com", user.getEmail(), "Восстановление пароля", url));

    return null;
  }
}
