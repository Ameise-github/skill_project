package skill.project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileRequest {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private MultipartFile photo;
  private String name;
  private String email;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String password;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private boolean removePhoto;
}
