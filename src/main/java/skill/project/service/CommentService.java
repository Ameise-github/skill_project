package skill.project.service;

import skill.project.dto.request.CommentRequest;

import java.util.Map;

public interface CommentService {
  Map<String,Integer> addComment(Integer userId, CommentRequest newComment);
}
