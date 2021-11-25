package skill.project.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModeratorRequest {
  @JsonProperty("post_id")
  private Integer postId;
  private String decision;
}
