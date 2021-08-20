package skill.project.service;

import skill.project.dto.request.RegisterRequest;
import skill.project.dto.response.RegisterResponse;

public interface RegistrationService {
  RegisterResponse register(RegisterRequest registerNew);
}
