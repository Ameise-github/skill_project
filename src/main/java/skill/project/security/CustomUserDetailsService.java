package skill.project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skill.project.model.User;
import skill.project.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User userModel = userRepository.findByEmailLike(email);
    if (userModel == null)
      throw new UsernameNotFoundException("Пользователь с email " + email + " не найден!");
    return new CustomUser(userModel);
  }
}
