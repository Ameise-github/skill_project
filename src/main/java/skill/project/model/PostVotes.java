package skill.project.model;

import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_votes")
@Check(constraints = "value = 1 or value = -1") // 1 - like; -1 -- dis like
public class PostVotes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "post_id")
  Post post;

  @Column(name = "time")
  LocalDateTime timeCreate;

  @NotNull
  int value;
}
