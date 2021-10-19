package skill.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "v_social_info")
public class SocialInfo {
  @Id
  @Column(name = "obj_id")
  private Integer objId;

  @Column(name = "likes_count")
  private Integer likeCount;

  @Column(name = "dislikes_count")
  private Integer dislikeCount;

  @Column(name = "comment_count")
  private int commentCount;

}
