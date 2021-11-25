package skill.project.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PostError implements ResponseErrors {
  private String title;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String text;

  @Override
  public boolean isEmpty() {
    return (this.title == null || this.title.isEmpty())
        && (this.text == null || this.text.isEmpty());
  }
}
