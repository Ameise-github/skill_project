package skill.project.model;

import java.time.LocalDateTime;

public interface StatisticModel {
  int getPostsCount();

  int getLikesCount();

  int getDislikesCount();

  int getViewsCount();

  LocalDateTime getFirstPublication();
}
