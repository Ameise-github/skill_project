package skill.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import skill.project.model.Post;
import skill.project.model.SocialInfo;
import skill.project.model.Tag;
import skill.project.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDto {
  private int id;
  private long timestamp;
  private UserDto user;
  private String title;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String announce;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String text;
  private int likeCount;
  private int dislikeCount;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer commentCount;
  private Integer viewCount;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean active;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<String> tags;
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

  public static PostDto getFullPostDto(Post post, SocialInfo socialInfo) {
    PostDto dto = new PostDto(post, socialInfo);
    dto.setCommentCount(null);
    dto.setAnnounce(null);
    dto.setTags(post.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
    dto.setActive(post.isActive());
    dto.setText(post.getText());
    return dto;
  }
}
