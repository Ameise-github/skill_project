package skill.project.model.enums;

public enum ModeType {
  recent("по дате публикации"),
  popular("кол-во комментариев"),
  best("кол-во лайков"),
  early("по дате публикации (старые)");

  private final String lable;

  ModeType(String l) {
    lable = l;
  }

  public String getLable() {
    return lable;
  }
}
