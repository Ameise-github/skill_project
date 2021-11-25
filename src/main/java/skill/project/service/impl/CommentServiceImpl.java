package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import skill.project.dto.error.AnyError;
import skill.project.dto.request.CommentRequest;
import skill.project.dto.response.Response;
import skill.project.exeption.AppLogicException;
import skill.project.exeption.NotFoundException;
import skill.project.model.Post;
import skill.project.model.PostComments;
import skill.project.repository.CommentRepository;
import skill.project.repository.PostRepository;
import skill.project.repository.UserRepository;
import skill.project.service.CommentService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  @Override
  public Map<String, Integer> addComment(Integer userId, CommentRequest newComment) {
    Post post = postRepository
        .findById(newComment.getPostId()).orElseThrow(() -> new NotFoundException("Пост не найден", HttpStatus.BAD_REQUEST));

    PostComments parentCom = null;
    if (newComment.getParentId() != null && newComment.getParentId() != 0){
      parentCom = commentRepository
          .findById(newComment.getParentId())
          .orElseThrow(() -> new NotFoundException("Комментарий не найден", HttpStatus.BAD_REQUEST));
    }
    if (newComment.getText() == null || newComment.getText().isEmpty() || newComment.getText().length() < 15) {
      throw new AppLogicException(
          HttpStatus.BAD_REQUEST,
          new Response(false, new AnyError("Текст комментария не задан или слишком короткий"))
      );
    }
    PostComments comment = new PostComments(parentCom, post, userRepository.getById(userId), LocalDateTime.now(), newComment.getText());
    commentRepository.save(comment);

    Map<String, Integer> res = new HashMap<>();
    res.put("id", comment.getId());
    return res;
  }
}
