package skill.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProjectConfig {

  @Bean
  public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter mapping = new MappingJackson2HttpMessageConverter();
    List<MediaType> mediaTypes = new ArrayList<>(mapping.getSupportedMediaTypes());
    mediaTypes.add(MediaType.MULTIPART_FORM_DATA);
    mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
    mapping.setSupportedMediaTypes(mediaTypes);
    return mapping;
  }

}
