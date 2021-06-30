package skill.project.model;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;
import skill.project.enums.ModeratorEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class PostModel {
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotNull
  @Column(name = "is_active")
  boolean active;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "moderation_status",columnDefinition = "moderation_status_enum default NEW")
  @ColumnTransformer(read = "state::varchar", write = "?::moderation_status_enum")
  ModeratorEnum moderationStatus;

  @Column(name = "moderator_id")
  Integer moderatorId;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  UserModel user;

  @NotNull
  @Column(name = "time")
  LocalDateTime timeCreate;

  @NotNull
  String title;

  @NotNull
  String text;

  @Column(name = "view_count")
  Integer viewCount;

  @ManyToMany
  @JoinTable(name = "tag2post",
  joinColumns = @JoinColumn(name = "post_id"),
  inverseJoinColumns = @JoinColumn(name = "tag_id"))
  List<TagModel> tags;
}
