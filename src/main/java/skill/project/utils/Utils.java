package skill.project.utils;

import org.jsoup.Jsoup;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
    //TODO проверить правильность перевода
    return dateTime.atZone(ZoneOffset.UTC).toInstant().getEpochSecond();
  }

  public static String shortTextNotHTML(String text) {
    String  doc = Jsoup.parse(text).body().text();
    return doc.length() > 150 ? doc.substring(0, 150) + "..." : doc;
  }
}
