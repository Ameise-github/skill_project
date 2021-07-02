package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.project.model.GlobalSettingsModel;


public interface GlobalSettingsRepository extends JpaRepository<GlobalSettingsModel, Integer> {

}
