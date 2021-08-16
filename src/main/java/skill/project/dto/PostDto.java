package skill.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.core.metrics.StartupStep;
import skill.project.model.Post;
import skill.project.model.PostComments;
import skill.project.model.SocialInfo;
import skill.project.model.Tag;
import skill.project.utils.Utils;

import javax.xml.stream.events.Comment;
import java.util.List;

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

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private boolean active;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Tag> tags;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<CommentDto> comments;

  public PostDto (Post post, SocialInfo socialInfo) {
      this.id = post.getId();
      this.timestamp = Utils.getTimestamp(post.getTimeCreate());
      this.user = UserDto.
          builder()
          .id(post.getUser().getId())
          .name(post.getUser().getName())
          .build();
      this.title = post.getTitle();
      this.announce = Utils.shortTextNotHTML(post.getText());
      this.likeCount = socialInfo.getLikeCount();
      this.dislikeCount = socialInfo.getDislikeCount();
      this.commentCount = socialInfo.getCommentCount();
      this.viewCount = post.getViewCount() == null ? 0 : post.getViewCount();
  }
}
