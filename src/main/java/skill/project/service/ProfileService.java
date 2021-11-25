package skill.project.service;

import skill.project.dto.request.ProfileRequest;
import skill.project.dto.request.RegisterRequest;
import skill.project.dto.response.Response;
import skill.project.security.CustomUser;

public interface ProfileService {
  Response register(RegisterRequest registerNew);

  Response editedProfile(CustomUser principal, ProfileRequest profile);
}
