package skill.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class UserDto {
  private int id;
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String photo;
}
