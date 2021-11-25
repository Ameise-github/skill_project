package skill.project.dto.error;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImgError implements ResponseErrors {
  private String image;

  @Override
  public boolean isEmpty() {
    return image == null || image.isEmpty();
  }
}
