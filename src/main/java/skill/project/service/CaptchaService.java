package skill.project.service;

import com.github.cage.Cage;
import com.github.cage.ObjectRoulette;
import com.github.cage.image.ConstantColorGenerator;
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
    Painter painter = new Painter(200, 80, Color.WHITE,  Painter.Quality.MAX,  new EffectConfig(), new Random());
    Cage cage = new Cage(painter, new ObjectRoulette(new Random(), new Font[]{new Font("Monospaced", 0, 35)}), new ConstantColorGenerator(Color.BLUE), "png", 0F, null, new Random());
    String token = cage.getTokenGenerator().next();
    String secret = UUID.randomUUID().toString().replace("-","");

    String s = Base64.getEncoder().encodeToString(cage.draw(token));
    res.setImage("data:image/png;base64, " + s);
    res.setSecret(secret);
    CaptchaCode model = new CaptchaCode(LocalDateTime.now(), token, secret);
    captchaRepository.save(model);

    return res;
  }
}
