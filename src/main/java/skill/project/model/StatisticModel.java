package skill.project.model;

import java.time.LocalDateTime;

public interface StatisticModel {
  Integer getPostsCount();

  Integer getLikesCount();

  Integer getDislikesCount();

  Integer getViewsCount();

  LocalDateTime getFirstPublication();
}
