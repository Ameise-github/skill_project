package skill.project.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_comments")
public class PostCommentsModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(name = "parent_id")
  Integer parentId;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "post_id")
  PostModel post;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  UserModel user;

  @NotNull
  LocalDateTime time;

  @NotNull
  String text;
}
