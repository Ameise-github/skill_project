package skill.project.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterError implements ResponseErrors {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String email;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String name;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String password;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String captcha;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String photo;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String code;



  //TODO проверить на изменение профиля где нет капчи
  @Override
  public boolean isEmpty() {
    return (email == null || email.isEmpty())
        && (name == null || name.isEmpty())
        && (password == null || password.isEmpty())
        && (captcha == null || captcha.isEmpty());
  }
}
