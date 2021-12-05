package skill.project.dto.error;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ResponseErrors {
  @JsonIgnore
  boolean isEmpty();
}
