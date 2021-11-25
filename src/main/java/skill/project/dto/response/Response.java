package skill.project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import skill.project.dto.error.ResponseErrors;

@Setter
@Getter
@AllArgsConstructor
public class Response {
  private Boolean result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ResponseErrors errors;

  public Response(Boolean result) {
    this.result = result;
  }
}
