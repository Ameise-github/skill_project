package skill.project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import skill.project.dto.RegisterError;

@Setter
@Getter
@AllArgsConstructor
public class RegisterResponse {
  private Boolean result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private RegisterError errors;

  public RegisterResponse(Boolean result) {
    this.result = result;
  }
}
