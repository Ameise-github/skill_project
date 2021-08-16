package skill.project.dto;

import lombok.Data;
import skill.project.model.PostComments;
import skill.project.utils.Utils;

@Data
public class CommentDto {
  private Integer id;
  private long timestamp;
  private String text;
  private UserDto user;

  public CommentDto(PostComments comment) {
    this.id = comment.getId();
    this.text = comment.getText();
    this.user = UserDto.builder()
        .id(comment.getUser().getId())
        .name(comment.getUser().getName())
        .photo(comment.getUser().getPhotoUrl())
        .build();
    this.timestamp = Utils.getTimestamp(comment.getTime());
  }
}
