package skill.project.model;

import java.math.BigDecimal;

public interface TagStatisticEntity {
  Integer getTagId();

  String getName();

  Integer getCountTg();

  BigDecimal getWeight();
}
