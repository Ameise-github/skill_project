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
    this.postsCount = statistic.getPostsCount();
    this.likesCount = statistic.getLikesCount();
    this.dislikesCount = statistic.getDislikesCount();
    this.viewsCount = statistic.getViewsCount();
    this.firstPublication = Utils.getTimestamp(statistic.getFirstPublication());
  }
}
