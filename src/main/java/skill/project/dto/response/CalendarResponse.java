package skill.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CalendarResponse {
  List<Integer> years;
  Map<String, BigInteger> posts;
}
