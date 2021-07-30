package skill.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skill.project.dto.TagDto;
import skill.project.dto.response.TagResponse;
import skill.project.model.GlobalSettings;
import skill.project.model.TagStatisticEntity;
import skill.project.repository.GlobalSettingsRepository;
import skill.project.service.GeneralService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {
  private final GlobalSettingsRepository globalSettingsRepository;
  private final EntityManager em;

  @Override
  public Map<String, Boolean> settingsMap() {
    List<GlobalSettings> settings = globalSettingsRepository.findAll();
    Map<String, Boolean> settingsMap = settings.stream().collect(Collectors.toMap(GlobalSettings::getCode, v -> v.getValue().equals("YES")));
    return settingsMap;
  }

  @Override
  public TagResponse getTags(String query){
    String qs = "select tt.tg_id, tt.name, tt.c_tg, tt.c_all_p , trunc(cast(tt.c_tg as numeric) / cast(tt.c_all_p as numeric), 2) weight\n" +
        "from (\n" +
        "    select tg.id tg_id, tg.name , count(t2p.post_id) c_tg, p.c_all_p\n" +
        "    from tags tg\n" +
        "    left join tag2post  t2p on tg.id = t2p.tag_id\n" +
        "    right join  (select p.id, count(*) over() c_all_p\n" +
        "            from posts p\n" +
        "            where p.is_active = true\n" +
        "            and p.moderation_status = 'ACCEPTED'\n" +
        "            and p.time <= now()) p on p.id = t2p.post_id\n" +
        "    where lower(tg.name) like lower(:tag  || '%')\n" +
        "    group by tg.id,p.c_all_p\n" +
        "         )tt";
    Query q = em.createNativeQuery(qs, TagStatisticEntity.class);
    q.setParameter("tag", query == null ? "" : query);
    List<TagStatisticEntity> allTag = q.getResultList();
    Double maxW = allTag.stream().max((tg1, tg2) -> tg1.getCountTg().compareTo(tg2.getCountTg())).get().getWeight();
    Double k = 1 / maxW;
    TagResponse res = new TagResponse(allTag
        .stream()
        .map(t -> new TagDto(t.getName(), (t.getWeight() == null ? 0 : t.getWeight() * k)))
        .collect(Collectors.toList()));
    return res;
  }
}
