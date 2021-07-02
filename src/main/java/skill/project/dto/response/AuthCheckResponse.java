package skill.project.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import skill.project.model.UserModel;

@Getter
@Setter
@Builder
public class AuthCheckResponse {
  private boolean result;
  private UserModel user;
}
