package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.project.model.GlobalSettings;


public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {

}
