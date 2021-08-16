package skill.project.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import org.springframework.stereotype.Service;
import skill.project.dto.response.CaptchaResponse;

@Service
public class CaptchaService {
  private static final Cage cage = new GCage();

  public CaptchaResponse generateCaptcha() {

    return null;
  }
}
