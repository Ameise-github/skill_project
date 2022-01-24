package skill.project.service;

import skill.project.dto.request.ModeratorRequest;
import skill.project.dto.response.CalendarResponse;
import skill.project.dto.response.Response;
import skill.project.dto.response.StatisticResponse;
import skill.project.dto.response.TagResponse;
import skill.project.security.CustomUser;

import java.util.Map;

public interface GeneralService {
  Map<String, Boolean> settingsMap();

  TagResponse getTags(String query);

  CalendarResponse getCalendarPosts(String year);

  Response moderationPost(CustomUser principal, ModeratorRequest moderatorPost);

  StatisticResponse myStatistics(Integer userId);

  StatisticResponse allStatistics(CustomUser principal);

  void editedSettings(Map<String, Boolean> settings);
}
