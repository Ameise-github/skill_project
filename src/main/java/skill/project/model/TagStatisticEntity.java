package skill.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class TagStatisticEntity {
  @Id
  @Column(name = "tg_id")
  private Integer tagId;

  private String name;

  @Column(name = "c_tg")
  private Integer countTg;

  @Column(name = "c_all_p")
  private Integer countAllPost;

  private Double weight;
}
