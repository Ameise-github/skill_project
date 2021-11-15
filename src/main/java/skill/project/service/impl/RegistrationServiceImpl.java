package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import skill.project.dto.RegisterError;
import skill.project.dto.request.RegisterRequest;
import skill.project.dto.response.RegisterResponse;
import skill.project.model.CaptchaCode;
import skill.project.model.User;
import skill.project.repository.CaptchaCodeRepository;
import skill.project.repository.UserRepository;
import skill.project.service.RegistrationService;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
  private final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  private final CaptchaCodeRepository captchaRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public RegisterResponse register(RegisterRequest registerNew) {
    RegisterError error = validData(registerNew);
    if (!error.isEmpty()) {
      return new RegisterResponse(false, error);
    }

    User user = userRepository.findByEmailLike(registerNew.getEmail());
    if (user != null) {
      if (error.getEmail() == null || error.getEmail().isEmpty()) {
        error.setEmail("Этот e-mail уже зарегистрирован");
      }
    }

    if (error.isEmpty()) {
      User newUser = new User(LocalDateTime.now(), registerNew.getName(), registerNew.getEmail(), passwordEncoder.encode(registerNew.getPassword()));
      userRepository.save(newUser);
    }

    return error.isEmpty() ? new RegisterResponse(true) : new RegisterResponse(false, error);
  }

  private RegisterError validData(RegisterRequest registerNew) {
    RegisterError error = new RegisterError();
    if (registerNew.getName() == null || registerNew.getName().isEmpty()
        || registerNew.getPassword() == null || registerNew.getPassword().isEmpty()
        || registerNew.getEmail() == null || registerNew.getEmail().isEmpty()) {
      error.setEmail("E-mail не должен быть пустым");
      error.setName("ФИО не должно быть пустым");
      error.setPassword("Заполните пароль");
      return error;
    }

    if (registerNew.getPassword().length() < 6) {
      error.setPassword("Пароль короче 6-ти символов");
    }

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(registerNew.getEmail());
    if (!matcher.matches()) {
      error.setEmail("Проверьте правильность ввода адреса электронной почты");
    }

    CaptchaCode captcha = captchaRepository.findBySecretCode(registerNew.getCaptchaSecret());
    if (!captcha.getCode().equals(registerNew.getCaptcha())) {
      error.setCaptcha("Код с картинки введён неверно");
    }
    return error;
  }
}
