package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmailLike(String email);

  @Query(value = "from User u where u.code = ?1")
  User findByCode(String code);
}
