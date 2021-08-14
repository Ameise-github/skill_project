package skill.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skill.project.dto.TagDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
  List<TagDto> tags;
}
