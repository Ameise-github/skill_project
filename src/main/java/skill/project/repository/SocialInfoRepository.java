package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.SocialInfo;

import java.util.List;

public interface SocialInfoRepository extends JpaRepository<SocialInfo, Integer> {
  @Query(value = "select s from SocialInfo s where s.objId in ?1")
  List<SocialInfo> getAllPosts(List<Integer> postIds);
}
