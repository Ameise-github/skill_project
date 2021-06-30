package skill.project.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "global_settings")
public class GlobalSettingsModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String code;
  String name;
  String value;
}
