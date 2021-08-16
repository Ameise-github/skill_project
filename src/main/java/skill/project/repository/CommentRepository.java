package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.project.model.Post;
import skill.project.model.PostComments;

import java.util.List;

public interface CommentRepository extends JpaRepository<PostComments, Integer> {
  List<PostComments> getPostCommentsByPost(Post post);
}
