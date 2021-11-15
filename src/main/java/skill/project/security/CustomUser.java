package skill.project.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import skill.project.model.User;

import java.util.Collection;

@Data
public class CustomUser implements UserDetails {
  private Integer id = 0;
  private String photo;
  private boolean moderation;
  private String name;
  private String password;
  private String email;
  private Collection<SimpleGrantedAuthority> authorities;

  public CustomUser (User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.password = user.getPassword();
    this.photo = user.getPhotoUrl();
    this.moderation = user.isModerator();
    this.authorities = user.getRole().getAuthority();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
