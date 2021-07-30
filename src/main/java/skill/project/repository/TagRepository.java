package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.project.model.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
  List<Tag> findAllByNameLike(String tag);
}
