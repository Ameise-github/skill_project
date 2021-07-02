package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skill.project.model.GlobalSettingsModel;
import skill.project.repository.GlobalSettingsRepository;
import skill.project.service.GeneralService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {
  private final GlobalSettingsRepository globalSettingsRepository;

  @Override
  public Map<String, Boolean> settingsMap() {
    List<GlobalSettingsModel> settings = globalSettingsRepository.findAll();
    Map<String, Boolean> settingsMap = settings.stream().collect(Collectors.toMap(GlobalSettingsModel::getCode, v -> v.getValue().equals("YES")));
    return settingsMap;
  }
}
