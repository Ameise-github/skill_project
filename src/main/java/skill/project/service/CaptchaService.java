package skill.project.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.github.cage.image.Painter;
import org.springframework.stereotype.Service;
import skill.project.dto.response.CaptchaResponse;

import java.awt.*;

@Service
public class CaptchaService {

  public CaptchaResponse generateCaptcha() {
    Cage cage = new GCage();
    String token = cage.getTokenGenerator().next();
    cage.draw(token);

    //v2
//    Painter painter = new Painter(100, 35, Color.LIGHT_GRAY,  Painter.Quality.MAX, Painter.Quality.MAX)
    return null;
  }
}
