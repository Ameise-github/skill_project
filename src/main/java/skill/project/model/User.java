package skill.project.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(name = "is_moderator")
  boolean moderator;

  @NotNull
  @Column(name = "reg_time")
  LocalDateTime regTime;

  @NotNull
  String name;

  @NotNull
  String email;

  @NotNull
  String password;

  String code;

  @Column(name = "photo")
  String photoUrl;

  public User(LocalDateTime regTime, String name, String email, String password) {
    this.regTime = regTime;
    this.name = name;
    this.email = email;
    this.password = password;
  }

}
