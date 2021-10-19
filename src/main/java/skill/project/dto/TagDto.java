package skill.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TagDto {
  private String name;
  private BigDecimal weight;
}
