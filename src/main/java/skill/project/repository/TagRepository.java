package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.Tag;
import skill.project.model.TagStatisticEntity;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
  List<Tag> findAllByNameLike(String tag);

  @Query(value = "with pst as (\n" +
      "    select count(*) c_all_p\n" +
      "    from posts p\n" +
      "    where p.is_active = true\n" +
      "        and p.moderation_status = 'ACCEPTED'\n" +
      "        and p.time <= now()\n" +
      ")\n" +
      "   select tt.tg_id as tagId, tt.name as name,\n" +
      "          cast(tt.c_tg as int) countTg,\n" +
      "          trunc(cast(tt.c_tg as numeric(5,2)) / cast((select pst.c_all_p from pst) as numeric(5,2)), 2) weight\n" +
      "   from (select tags.id tg_id, tags.name , count(t2p.post_id) c_tg\n" +
      "        from tags\n" +
      "        left join tag2post t2p on tags.id = t2p.tag_id\n" +
      "        group by tags.id\n" +
      ")tt\n" +
      "where lower(tt.name) like lower(?1 || '%')"
    , nativeQuery = true)
  List<TagStatisticEntity> getStatistic(String nameTag);
}
