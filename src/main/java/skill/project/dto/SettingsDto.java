package skill.project.dto;

import lombok.Data;

@Data
public class SettingsDto {
  private boolean MULTIUSER_MODE;
  private boolean POST_PREMODERATION;
  private boolean STATISTICS_IS_PUBLIC;
}
