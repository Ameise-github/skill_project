package skill.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CalendarResponse {
  List<Integer> years;
  Map<String, Integer> posts;
}
