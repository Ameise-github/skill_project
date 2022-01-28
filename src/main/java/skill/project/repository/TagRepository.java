package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.Tag;
import skill.project.model.TagStatisticEntity;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
  List<Tag> findAllByNameLike(String tag);

  @Query(value = " with pst as (\n" +
      "        select count(*) c_all_p\n" +
      "        from posts p\n" +
      "        where p.is_active\n" +
      "            and p.moderation_status = 'ACCEPTED'\n" +
      "            and p.time <= now()\n" +
      "      )\n" +
      "select  t.id as tagId, t.name, count(t2p.post_id) countTg, cast((count(t2p.post_id) / (select pst.c_all_p from pst)) as decimal(5,2)) weight\n" +
      "from posts p\n" +
      "    right join tag2post t2p on p.id = t2p.post_id\n" +
      "    left join tags t on t2p.tag_id = t.id\n" +
      "where p.is_active = true\n" +
      "  and p.moderation_status = 'ACCEPTED'\n" +
      "  and p.time <= now()\n" +
      "  and  lower(t.name) like lower(CONCAT(?1,'%'))\n" +
      "group by t.id"
    , nativeQuery = true)
  List<TagStatisticEntity> getStatistic(String nameTag);

  @Query(value = "select t from Tag t where t.name in ?1")
  List<Tag> findAllByName(List<String> tags);
}
