package skill.project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileRequest {
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
//  private MultipartFile photo;
  private Object photo;
  private String name;
  private String email;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String password;
  private boolean removePhoto;
}
