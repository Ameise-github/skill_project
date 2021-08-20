package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.project.model.CaptchaCode;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {
  CaptchaCode findBySecretCode(String code);
}
