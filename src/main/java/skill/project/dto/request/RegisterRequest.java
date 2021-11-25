package skill.project.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @JsonProperty("e_mail")
  private String email;
  private String password;
  private String name;
  private String captcha;
  @JsonProperty("captcha_secret")
  private String captchaSecret;
}
