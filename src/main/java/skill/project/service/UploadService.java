package skill.project.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
  String uploadImage(MultipartFile image);
}
