package skill.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SocialInfo {
  @Id
  @Column(name = "obj_id")
  private int objId;

  @Column(name = "like_c")
  private int likeCount;

  @Column(name = "dislike_c")
  private int dislikeCount;

  @Column(name = "comment_c")
  private int commentCount;

}
