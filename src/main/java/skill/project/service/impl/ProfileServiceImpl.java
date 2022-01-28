package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skill.project.dto.error.RegisterError;
import skill.project.dto.request.ProfileRequest;
import skill.project.dto.request.RegisterRequest;
import skill.project.dto.response.Response;
import skill.project.exeption.AppLogicException;
import skill.project.model.CaptchaCode;
import skill.project.model.User;
import skill.project.repository.CaptchaCodeRepository;
import skill.project.repository.UserRepository;
import skill.project.security.CustomUser;
import skill.project.service.ProfileService;
import skill.project.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
  @Value("${upload.profileSize}")
  private String uploadFileSize;
  private final long MBToBates = 1048576L;
  @Value("${upload.profileDir}")
  private String uploadDir;

  private final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  private final CaptchaCodeRepository captchaRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Response register(RegisterRequest registerNew) {
    RegisterError error = validData(registerNew, false);
    if (!error.isEmpty()) {
      return new Response(false, error);
    }

    User newUser = new User(LocalDateTime.now(ZoneOffset.UTC), registerNew.getName(), registerNew.getEmail(), passwordEncoder.encode(registerNew.getPassword()));
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
            .build(), true);
    if (!error.isEmpty()) {
      return new Response(false, error);
    }

    user.setEmail(profile.getEmail());
    user.setName(profile.getName());
    principal.setName(user.getName());
    principal.setEmail(profile.getEmail());

    if (profile.getPassword() != null && !profile.getPassword().isEmpty()) {
      String password = passwordEncoder.encode(profile.getPassword());
      user.setPassword(password);
      principal.setPassword(password);
    }

    if (profile.isRemovePhoto()) {
      user.setPhotoUrl(null);
      principal.setPhoto(null);
    } else if (profile.getPhoto() != null) {
      try {
        String pathPhoto = editPhoto((MultipartFile) profile.getPhoto());
        user.setPhotoUrl(pathPhoto);
        principal.setPhoto(pathPhoto);
      } catch (AppLogicException ex) {
        error.setPhoto(ex.getMessage());
        return new Response(false, error);
      }
    }

    userRepository.save(user);
    return new Response(true);
  }

  @Override
  public Response editedPassword(RegisterRequest request) {
    RegisterError error = new RegisterError();
    User user = userRepository.findByCode(request.getCode());
    if (user == null) {
      error.setCode("Ссылка для восстановления пароля устарела. <a href=\"/auth/restore\">Запросить ссылку снова</a>");
    }

    CaptchaCode captcha = captchaRepository.findBySecretCode(request.getCaptchaSecret());
    if (!captcha.getCode().equals(request.getCaptcha())) {
      error.setCaptcha("Код с картинки введён неверно");
    }
    if (request.getPassword().length() < 6) {
      error.setPassword("Пароль короче 6-ти символов");
    }

    if (error.isEmpty() && error.getCode() == null) {
      user.setPassword(passwordEncoder.encode(request.getPassword()));
      user.setCode(null);
      userRepository.save(user);
      return new Response(true);
    } else {
      return new Response(false, error);
    }
  }

  private RegisterError validData(RegisterRequest registerNew, boolean isEdit) {
    RegisterError error = new RegisterError();
    if (registerNew.getName() == null || registerNew.getName().isEmpty()
        || registerNew.getEmail() == null || registerNew.getEmail().isEmpty()) {
      error.setEmail("E-mail не должен быть пустым");
      error.setName("ФИО не должно быть пустым");

      if (!isEdit &&
          (registerNew.getPassword() == null || registerNew.getPassword().isEmpty())
      ) {
        error.setPassword("Заполните пароль");
      }

      return error;
    }

    if (registerNew.getPassword() != null && registerNew.getPassword().length() < 6) {
      error.setPassword("Пароль короче 6-ти символов");
    }

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(registerNew.getEmail());
    if (!matcher.matches()) {
      error.setEmail("Проверьте правильность ввода адреса электронной почты");
    }

    if ((error.getEmail() == null || error.getEmail().isEmpty()) && !isEdit) {
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

  private String editPhoto(MultipartFile photo) {
    if (!Utils.uploadImage(photo.getContentType())) {
      throw new AppLogicException("Неверный формат изображения");
    }

    if (photo.getSize() > (Integer.parseInt(uploadFileSize) * MBToBates)) {
      throw new AppLogicException("Фото слишком большое, нужно не более 5 Мб");
    } else {
      try {
        String filename = photo.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf("."));

        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
          Files.createDirectories(path);
        }

        Path pathProfile = path.resolve("profile-" + UUID.randomUUID().toString().replace("-", "") + ext);
        InputStream inputStream = photo.getInputStream();
        Thumbnails.of(inputStream)
            .size(36, 36)
            .outputQuality(0.8)
            .toFile(pathProfile.toString());

        return pathProfile.toString();
      } catch (IOException e) {
        e.printStackTrace();
        throw new AppLogicException("Не удалось загрузить фото, попробуйте позже");
      }
    }
  }
}
