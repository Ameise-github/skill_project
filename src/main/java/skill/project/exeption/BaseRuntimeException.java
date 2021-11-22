package skill.project.exeption;

import org.springframework.http.HttpStatus;

public class BaseRuntimeException extends RuntimeException{
  private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
  private Object data;

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public BaseRuntimeException() {
    super();
  }

  public BaseRuntimeException(String message) {
    super(message);
  }

  public BaseRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public BaseRuntimeException(Throwable cause) {
    super(cause);
  }

  public BaseRuntimeException(String message, Throwable cause, HttpStatus httpStatus) {
    super(message, cause);
    this.httpStatus = httpStatus;
  }

  public BaseRuntimeException(String message, Throwable cause, HttpStatus httpStatus, Object data) {
    super(message, cause);
    this.httpStatus = httpStatus;
    this.data = data;
  }
}
