package skill.project.service;

import skill.project.dto.response.CalendarResponse;
import skill.project.dto.response.TagResponse;

import java.util.Map;

public interface GeneralService {
  Map<String, Boolean> settingsMap();

  TagResponse getTags(String query);

  CalendarResponse getCalendarPosts(String year);
}
