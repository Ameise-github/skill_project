package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skill.project.dto.TagDto;
import skill.project.dto.response.CalendarResponse;
import skill.project.dto.response.TagResponse;
import skill.project.model.GlobalSettings;
import skill.project.model.TagStatisticEntity;
import skill.project.repository.GlobalSettingsRepository;
import skill.project.repository.PostRepository;
import skill.project.service.GeneralService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {
  private final GlobalSettingsRepository globalSettingsRepository;
  private final PostRepository postRepository;
  private final EntityManager em;

  @Override
  public Map<String, Boolean> settingsMap() {
    List<GlobalSettings> settings = globalSettingsRepository.findAll();
    return settings.stream().collect(Collectors.toMap(GlobalSettings::getCode, v -> v.getValue().equals("YES")));
  }

  @Override
  public TagResponse getTags(String query){
    String qs = "select * from v_tag_info tg where lower(tg.name) like lower(:tag  || '%')";
    Query q = em.createNativeQuery(qs, TagStatisticEntity.class);
    q.setParameter("tag", query == null ? "" : query);
    List<TagStatisticEntity> allTag = q.getResultList();
    if (allTag.size() != 0) {
      BigDecimal maxW = allTag.stream().max((tg1, tg2) -> tg1.getCountTg().compareTo(tg2.getCountTg())).get().getWeight();
      BigDecimal k = maxW.compareTo(BigDecimal.ZERO) != 0 ? BigDecimal.ONE.divide(maxW, RoundingMode.HALF_UP) : maxW;
      TagResponse res = new TagResponse(allTag
          .stream()
          .map(t -> new TagDto(t.getName(), (t.getWeight() == null ? BigDecimal.ZERO : t.getWeight().multiply(k))))
          .collect(Collectors.toList()));
      return res;
    }
    return new TagResponse();
  }

  @Override
  public CalendarResponse getCalendarPosts(String year) {
    if (year == null || year.isEmpty()) {
      year = String.valueOf(LocalDate.now().getYear());
    }
    List<Map<String, Object>> calPostsModel = postRepository.calendarPosts(Double.parseDouble(year));
    List<Integer> yearPosts = postRepository.getYearPosts();
    return new CalendarResponse(yearPosts, calPostsModel.stream().collect(Collectors.toMap(m -> (String)m.get("time"), v -> (BigInteger)v.get("count"))));
  }
}
