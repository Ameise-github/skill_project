package skill.project.exeption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<Object> NotFound(NotFoundException exception) {
    return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), exception.getHttpStatus(), null);
  }

  @ExceptionHandler(value = BaseRuntimeException.class)
  public ResponseEntity<Object> BaseRuntime(BaseRuntimeException exception) {
    return handleExceptionInternal(exception, exception.getData(), new HttpHeaders(), exception.getHttpStatus(), null);
  }
}
