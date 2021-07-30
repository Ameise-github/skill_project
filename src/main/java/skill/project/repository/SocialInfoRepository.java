package skill.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.SocialInfo;

import java.util.List;

public interface SocialInfoRepository extends JpaRepository<SocialInfo, Integer> {
  @Query(value = "select p.id as obj_id, coalesce(pv_l.c_l, 0) like_c, coalesce(pv_nl.c_nl, 0) dislike_c, count(pc.id) comment_c\n" +
      "      from posts p\n" +
      "      left join post_comments pc on p.id = pc.post_id\n" +
      "      left join (select pv1.post_id, count(pv1.id) c_l from post_votes pv1 where pv1.value = '1' group by pv1.post_id) pv_l on p.id = pv_l.post_id\n" +
      "      left join (select pv1.post_id, count(pv1.id) c_nl from post_votes pv1 where pv1.value = '-1' group by pv1.post_id) pv_nl on p.id = pv_nl.post_id\n" +
      "      where p.id in (?1)\n" +
      "      group by p.id,pv_l.c_l,pv_nl.c_nl", nativeQuery = true)
  List<SocialInfo> getAllPosts(List<Integer> postIds);
}
