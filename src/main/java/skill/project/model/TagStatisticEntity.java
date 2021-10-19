package skill.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "v_tag_info")
public class TagStatisticEntity {
  @Id
  @Column(name = "tg_id")
  private Integer tagId;

  private String name;

  @Column(name = "count_tg")
  private Integer countTg;

  private BigDecimal weight;
}
