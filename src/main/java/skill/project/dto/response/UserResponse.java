package skill.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skill.project.security.CustomUser;

@Data
@NoArgsConstructor
public class UserResponse {
  private int id;
  private String name;
  private String photo;
  private String email;
  private boolean moderation;
  private int moderationCount = 0;
  private boolean settings;

  public UserResponse(CustomUser customUser) {
    this.id = customUser.getId();
    this.name = customUser.getName();
    this.photo = customUser.getPhoto();
    this.email = customUser.getEmail();
    this.moderation = customUser.isModeration();
    this.settings = customUser.isModeration();
  }
}
