package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.project.model.PostVotes;

public interface PostVotesRepository extends JpaRepository<PostVotes, Integer> {
  PostVotes getByUser_Id(Integer userId);
}
