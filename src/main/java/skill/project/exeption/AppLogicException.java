package skill.project.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppLogicException extends BaseRuntimeException{
  private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
  private Object data; //todo поменять на ErrorResponse

  public AppLogicException(String message, Throwable cause, HttpStatus httpStatus, Object data) {
    super(message, cause, httpStatus, data);
  }

  public AppLogicException(HttpStatus httpStatus, Object data) {
    this.httpStatus = httpStatus;
    this.data = data;
  }
}
