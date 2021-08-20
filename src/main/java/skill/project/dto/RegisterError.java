package skill.project.dto;

import lombok.Data;

@Data
public class RegisterError {
  private String email;
  private String name;
  private String password;
  private String captcha;

  public boolean isEmpty() {
    return (email == null || email.isEmpty())
        && (name == null || name.isEmpty())
        && (password == null || password.isEmpty())
        && (captcha == null || captcha.isEmpty());
  }
}
