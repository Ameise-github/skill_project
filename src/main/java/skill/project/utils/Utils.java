package skill.project.utils;

import org.jsoup.Jsoup;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.*;

public class Utils {

  public static Pageable getPageable(long offset, int limit) {
    return getPageable(offset, limit, null);
  }

  public static Pageable getPageable(long offset, int limit, Sort sort) {
    int page = (int) (offset / limit);
    if (sort == null || sort.isEmpty()) {
      return PageRequest.of(page, limit);
    }
    return PageRequest.of(page, limit, sort);
  }

  public static long getTimestamp(LocalDateTime dateTime) {
    return dateTime.toInstant(ZoneOffset.UTC).getEpochSecond();
  }

  public static LocalDateTime getDateTime(long timestamp) {
    return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
  }

  public static String shortTextNotHTML(String text) {
    String  doc = Jsoup.parse(text).body().text();
    return doc.length() > 150 ? doc.substring(0, 150) + "..." : doc;
  }

  public static boolean uploadImage(String typeImg) {
    return "image/jpeg".equals(typeImg) || "image/png".equals(typeImg);
  }
}
