package skill.project.dto;

import lombok.Data;
import skill.project.model.Post;
import skill.project.model.SocialInfo;
import skill.project.utils.Utils;

@Data
public class PostDto {
  private int id;
  private long timestamp;
  private UserDto user;
  private String title;
  private String announce;
  private int likeCount;
  private int dislikeCount;
  private int commentCount;
  private Integer viewCount;

  public PostDto (Post post, SocialInfo socialInfo) {
      this.id = post.getId();
      this.timestamp = Utils.getTimestamp(post.getTimeCreate());
      this.user = new UserDto(post.getUser().getId(), post.getUser().getName());
      this.title = post.getTitle();
      this.announce = Utils.shortTextNotHTML(post.getText());
      this.likeCount = socialInfo.getLikeCount();
      this.dislikeCount = socialInfo.getDislikeCount();
      this.commentCount = socialInfo.getCommentCount();
      this.viewCount = post.getViewCount();
  }
}
