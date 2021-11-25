package skill.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_comments")
@NoArgsConstructor
public class PostComments {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  PostComments parentComment;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "post_id")
  Post post;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @NotNull
  LocalDateTime time;

  @NotNull
  String text;

  public PostComments(PostComments parentComment, Post post, User user, LocalDateTime time, String text) {
    this.parentComment = parentComment;
    this.post = post;
    this.user = user;
    this.time = time;
    this.text = text;
  }
}
