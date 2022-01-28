package skill.project.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skill.project.repository.CaptchaCodeRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class CaptchaTask {
  private final CaptchaCodeRepository captchaRepository;

  @Scheduled(cron = "${time.captcha.task}")
  public void clearCaptcha() {
    captchaRepository.deleteCaptcha(LocalDateTime.now(ZoneOffset.UTC));
  }
}
