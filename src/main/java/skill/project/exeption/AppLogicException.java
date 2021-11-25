package skill.project.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import skill.project.dto.response.Response;

@Getter
public class AppLogicException extends BaseRuntimeException{
  private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
  private Response data;

  public AppLogicException(String message, Throwable cause, HttpStatus httpStatus, Object data) {
    super(message, cause, httpStatus, data);
  }

  public AppLogicException(HttpStatus httpStatus, Response data) {
    this.httpStatus = httpStatus;
    this.data = data;
  }
}
