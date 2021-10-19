package skill.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "captcha_codes")
@NoArgsConstructor
public class CaptchaCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotNull
  LocalDateTime time;

  @NotNull
  String code;

  @NotNull
  @Column(name = "secret_code")
  String secretCode;

  public CaptchaCode(LocalDateTime time, String code, String secretCode) {
    this.time = time;
    this.code = code;
    this.secretCode = secretCode;
  }
}
