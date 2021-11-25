package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import skill.project.dto.error.RegisterError;
import skill.project.dto.request.ProfileRequest;
import skill.project.dto.request.RegisterRequest;
import skill.project.dto.response.Response;
import skill.project.model.CaptchaCode;
import skill.project.model.User;
import skill.project.repository.CaptchaCodeRepository;
import skill.project.repository.UserRepository;
import skill.project.security.CustomUser;
import skill.project.service.ProfileService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
  @Value("{$upload.profileSize}")
  private int uploadFileSize;
  private final long MBToBates = 1048576L;
  @Value("{$upload.profileDir}")
  private String uploadDir;

  private final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  private final CaptchaCodeRepository captchaRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Response register(RegisterRequest registerNew) {
    RegisterError error = validData(registerNew, true);
    if (!error.isEmpty()) {
      return new Response(false, error);
    }

    User newUser = new User(LocalDateTime.now(), registerNew.getName(), registerNew.getEmail(), passwordEncoder.encode(registerNew.getPassword()));
    userRepository.save(newUser);

    return new Response(true);
  }


  @Override
  public Response editedProfile(CustomUser principal, ProfileRequest profile) {
    User user = userRepository.getById(principal.getId());
    RegisterError error = validData(
        RegisterRequest.builder()
            .email(profile.getEmail())
            .password(profile.getPassword())
            .name(profile.getName())
            .build(), (profile.getPassword() != null && !profile.getPassword().isEmpty()));
    if (!error.isEmpty()) {
      //TODO проверить какой статус возвращать
      return new Response(false, error);
    }

    user.setEmail(profile.getEmail());
    user.setName(profile.getName());

    if (profile.getPassword() != null && !profile.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(profile.getPassword()));
    }

    if (profile.isRemovePhoto()) {
      user.setPhotoUrl(null);
    }
    if (!profile.isRemovePhoto() && profile.getPhoto() != null && !profile.getPhoto().isEmpty()) {
      if (profile.getPhoto().getSize() > (uploadFileSize * MBToBates)) {
        error.setPhoto("Фото слишком большое, нужно не более 5 Мб");
      } else {
        try {
          Resource resource = profile.getPhoto().getResource();
          //todo сделать красиво
          String filename = resource.getFilename();
          int index = filename.lastIndexOf(".");
          String ext = filename.substring(index);
          String fileName = uploadDir + File.separator + "profile-" + UUID.randomUUID() + ext;
          Thumbnails.of(resource.getFile())
              .size(36, 36)
              .outputQuality(0.8)
              .toFile(fileName);
          user.setPhotoUrl(fileName);
        } catch (IOException e) {
          e.printStackTrace();
          return new Response(false);
        }
      }
    }

    userRepository.save(user);
    return new Response(true);
  }

  private RegisterError validData(RegisterRequest registerNew, boolean editPassword) {
    RegisterError error = new RegisterError();
    if (registerNew.getName() == null || registerNew.getName().isEmpty()
        || registerNew.getEmail() == null || registerNew.getEmail().isEmpty()) {
      error.setEmail("E-mail не должен быть пустым");
      error.setName("ФИО не должно быть пустым");

      if (editPassword &&
          (registerNew.getPassword() == null || registerNew.getPassword().isEmpty())
      ) {
        error.setPassword("Заполните пароль");
      }

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

    if (error.getEmail() == null || error.getEmail().isEmpty()) {
      User user = userRepository.findByEmailLike(registerNew.getEmail());
      if (user != null) {
        if (error.getEmail() == null || error.getEmail().isEmpty()) {
          error.setEmail("Этот e-mail уже зарегистрирован");
        }
      }
    }

    if (registerNew.getCaptcha() != null && !registerNew.getCaptcha().isEmpty()) {
      CaptchaCode captcha = captchaRepository.findBySecretCode(registerNew.getCaptchaSecret());
      if (!captcha.getCode().equals(registerNew.getCaptcha())) {
        error.setCaptcha("Код с картинки введён неверно");
      }
    }

    return error;
  }
}
