package skill.project.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CaptchaResponse {
  private String secret;
  private String image;
}
