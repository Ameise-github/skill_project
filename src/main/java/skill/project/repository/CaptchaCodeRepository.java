package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import skill.project.model.CaptchaCode;

import java.time.LocalDateTime;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {
  CaptchaCode findBySecretCode(String code);

  @Modifying
  @Transactional
  @Query(value = "delete from CaptchaCode cc where cc.time <= ?1")
  void deleteCaptcha(LocalDateTime hour);
}
