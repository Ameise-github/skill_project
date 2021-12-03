package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import skill.project.dto.error.ImgError;
import skill.project.dto.response.Response;
import skill.project.exeption.AppLogicException;
import skill.project.service.UploadService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {
  @Value("{$upload.dir}")
  private String uploadDir;
  @Value("{$upload.maxSize}")
  private String maxSize;
  private final long MBToBates = 1048576L;

  @Override
  public String uploadImage(MultipartFile image) {
    if (image.getSize() > (Long.parseLong(maxSize) * MBToBates))
      throw new AppLogicException(
          HttpStatus.BAD_REQUEST,
          new Response(false, new ImgError("Размер файла превышает допустимый размер"))
      );

    String filename = image.getResource().getFilename();
    int index = filename.lastIndexOf(".");
    String ext = filename.substring(index + 1);
    if (!"jpg".equals(ext) || !"png".equals(ext))
      throw new AppLogicException(
          HttpStatus.BAD_REQUEST,
          new Response(false, new ImgError("Неверный формат изображения"))
      );

    String res = "";
    try {
      Path copyLocation = Paths
          .get(uploadDir + File.separator + StringUtils.cleanPath(image.getOriginalFilename()));
      Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
      res = copyLocation.toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw new AppLogicException(
          HttpStatus.BAD_REQUEST,
          new Response(false, new ImgError("Не удалось загрузить картинку, попробуйте еще раз."))
      );
    }

    return res;
  }
}
