package skill.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import skill.project.dto.PostDto;

import java.util.List;

@Getter
//@Setter
@AllArgsConstructor
public class PostResponse {
  long count;
  List<PostDto> posts;
}
