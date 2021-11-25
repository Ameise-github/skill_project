package skill.project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LikeRequest {
  @JsonProperty("post_id")
  private int postId;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  private int value;
}
