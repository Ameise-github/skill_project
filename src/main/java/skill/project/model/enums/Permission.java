package skill.project.model.enums;

public enum Permission {
  USER("user:write"),
  MODERATOR("user:moderator");

  private final String permission;

  Permission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
