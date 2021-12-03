package skill.project.model;

import lombok.Data;
import skill.project.model.enums.ModeratorEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotNull
  @Column(name = "is_active")
  boolean active;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "moderation_status")
  ModeratorEnum moderationStatus = ModeratorEnum.NEW;

  @ManyToOne
  @JoinColumn(name = "moderator_id")
  User moderator;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @NotNull
  @Column(name = "time")
  LocalDateTime timeCreate;

  @NotNull
  String title;

  @NotNull
  String text;

  @Column(name = "view_count")
  Integer viewCount;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tag2post",
  joinColumns = @JoinColumn(name = "post_id"),
  inverseJoinColumns = @JoinColumn(name = "tag_id"))
  List<Tag> tags;
}
