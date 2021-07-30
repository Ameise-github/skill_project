package skill.project.dto.response;

import lombok.AllArgsConstructor;
import skill.project.dto.TagDto;

import java.util.List;

@AllArgsConstructor
public class TagResponse {
  List<TagDto> tags;
}
