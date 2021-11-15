package skill.project.service;

import skill.project.dto.response.AuthResponse;
import skill.project.dto.request.LoginRequest;
import skill.project.security.CustomUser;

public interface LoginService {
  AuthResponse authCheck(CustomUser principal);

  AuthResponse login(LoginRequest request);
}
