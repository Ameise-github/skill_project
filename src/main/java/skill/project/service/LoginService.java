package skill.project.service;

import skill.project.dto.response.AuthResponse;
import skill.project.dto.request.LoginRequest;
import skill.project.dto.response.Response;
import skill.project.security.CustomUser;

import java.util.Map;

public interface LoginService {
  Response restorePassword(Map<String, String> body, String host);

  AuthResponse authCheck(CustomUser principal);

  AuthResponse login(LoginRequest request);
}
