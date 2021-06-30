package skill.project.model;

import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_votes")
@Check(constraints = "value = 1 or value = -1")
public class PostVotesModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotNull
  @Column(name = "user_id")
  int userId;

  @NotNull
  @Column(name = "post_id")
  int postId;

  @Column(name = "time")
  LocalDateTime timeCreate;

  @NotNull
  int value;
}
