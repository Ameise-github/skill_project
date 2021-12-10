package skill.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "global_settings")
public class GlobalSettings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String code;
  String name;
  boolean value;
}
