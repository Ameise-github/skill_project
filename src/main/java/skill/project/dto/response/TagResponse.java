package skill.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import skill.project.dto.TagDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
  List<TagDto> tags;
}
