package skill.project.dto.response;

import jdk.jshell.execution.Util;
import lombok.Data;
import lombok.NoArgsConstructor;
import skill.project.model.StatisticModel;
import skill.project.utils.Utils;

@Data
@NoArgsConstructor
public class StatisticResponse {
  private int postsCount;
  private int likesCount;
  private int dislikesCount;
  private int viewsCount;
  private long firstPublication;

  public StatisticResponse(StatisticModel statistic) {
    this.postsCount = statistic.getPostsCount() == null ? 0 : statistic.getPostsCount();
    this.likesCount = statistic.getLikesCount() == null ? 0 : statistic.getLikesCount();
    this.dislikesCount = statistic.getDislikesCount() == null ? 0 : statistic.getDislikesCount();
    this.viewsCount = statistic.getViewsCount() == null ? 0 : statistic.getViewsCount();
    this.firstPublication = statistic.getFirstPublication() == null ? 0 : Utils.getTimestamp(statistic.getFirstPublication());
  }
}
