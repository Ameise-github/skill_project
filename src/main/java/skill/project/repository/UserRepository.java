package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.project.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmailLike(String email);
}
