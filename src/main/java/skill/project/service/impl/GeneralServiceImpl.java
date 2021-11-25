package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import skill.project.dto.TagDto;
import skill.project.dto.request.ModeratorRequest;
import skill.project.dto.response.CalendarResponse;
import skill.project.dto.response.Response;
import skill.project.dto.response.TagResponse;
import skill.project.exeption.NotFoundException;
import skill.project.model.GlobalSettings;
import skill.project.model.Post;
import skill.project.model.TagStatisticEntity;
import skill.project.model.enums.ModeratorEnum;
import skill.project.repository.GlobalSettingsRepository;
import skill.project.repository.PostRepository;
import skill.project.repository.TagRepository;
import skill.project.security.CustomUser;
import skill.project.service.GeneralService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {
  private final GlobalSettingsRepository globalSettingsRepository;
  private final PostRepository postRepository;
  private final TagRepository tagRepository;

  @Override
  public Map<String, Boolean> settingsMap() {
    List<GlobalSettings> settings = globalSettingsRepository.findAll();
    return settings.stream().collect(Collectors.toMap(GlobalSettings::getCode, v -> v.getValue().equals("YES")));
  }

  @Override
  public TagResponse getTags(String query){
    List<TagStatisticEntity> allTag = tagRepository.getStatistic(query == null ? "" : query);
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

  @Override
  public Response moderationPost(CustomUser principal, ModeratorRequest moderatorPost) {
    Post post = postRepository.findById(moderatorPost.getPostId()).orElseThrow(() -> new NotFoundException("пост не найден", HttpStatus.BAD_REQUEST));
    Response res = new Response(false);
    if (principal.isAccountNonExpired()) {
      post.setModerationStatus(moderatorPost.getDecision().equals("accept") ? ModeratorEnum.ACCEPTED : ModeratorEnum.DECLINED);
//      post.setModerator();  todo прописать если нигде не задается
      postRepository.save(post);
      res.setResult(true);
    }

    return res;
  }
}
