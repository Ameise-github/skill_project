package skill.project.exeption;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
  private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  public NotFoundException() {super();}

  public NotFoundException(String message) {super(message);}

  public NotFoundException(String message, HttpStatus status) {
    super(message);
    this.httpStatus = status;
  }
}
