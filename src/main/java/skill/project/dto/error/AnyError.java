package skill.project.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnyError implements ResponseErrors{
  private String text;

  @Override
  public boolean isEmpty() {
    return text == null || text.isEmpty();
  }
}
