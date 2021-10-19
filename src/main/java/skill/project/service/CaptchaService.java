package skill.project.service;

import com.github.cage.Cage;
import com.github.cage.image.EffectConfig;
import com.github.cage.image.Painter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import skill.project.dto.response.CaptchaResponse;
import skill.project.model.CaptchaCode;
import skill.project.repository.CaptchaCodeRepository;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CaptchaService {
  private final CaptchaCodeRepository captchaRepository;
  @Value("${time.captcha.clear}")
  long timeCaptcha;

  public CaptchaResponse generateCaptcha() {
    CaptchaResponse res = new CaptchaResponse();
    Painter painter = new Painter(100, 35, Color.LIGHT_GRAY,  Painter.Quality.MAX,  new EffectConfig(), new Random());
    Cage cage = new Cage(painter, null, null, "png", Cage.DEFAULT_COMPRESS_RATIO, null, new Random());
    String token = cage.getTokenGenerator().next();
    String secret = UUID.randomUUID().toString().replace("-","");

    String s = Base64.getEncoder().encodeToString(cage.draw(token));
    res.setImage("data:image/png;base64, " + s);
    res.setSecret(secret);
    CaptchaCode model = new CaptchaCode(LocalDateTime.now(), token, secret);
    captchaRepository.save(model);

    //очистить таблицу
    clearCaptcha();
    return res;
  }

  private void clearCaptcha() {
    captchaRepository.deleteCaptcha(LocalDateTime.now().minusMinutes(timeCaptcha));
  }
}
