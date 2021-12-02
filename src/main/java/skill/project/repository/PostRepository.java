package skill.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.Post;
import skill.project.model.SocialInfo;
import skill.project.model.StatisticModel;
import skill.project.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "select t.id, is_active, moderation_status, moderator_id, text, time, title, view_count, user_id\n" +
        "from ( select p.*, count(pc.id) c_pc, coalesce(pv_l.cnt_l, 0) c_l\n" +
        "        from posts p\n" +
        "        left join post_comments pc on p.id = pc.post_id\n" +
        "        left join (select pv1.post_id, count(pv1.id) cnt_l from post_votes pv1 where pv1.value = '1' group by pv1.post_id) pv_l on p.id = pv_l.post_id\n" +
        "        where p.is_active = true and p.moderation_status = 'ACCEPTED' and p.time <= now()\n" +
        "        group by p.id, pv_l.cnt_l\n" +
        "    )t order by \n" +
        "         case when 'recent' = ?1 then t.time end desc, \n" +
        "         case when 'popular' = ?1 then t.c_pc end desc, \n" +
        "         case when 'best' = ?1 then t.c_l end desc, \n" +
        "         case when 'early' = ?1 then t.time end asc ", nativeQuery = true)
    Page<Post> getPosts(String sort, Pageable page);

  @Query(value = "select p from Post p where p.active = true and p.timeCreate <= current_date and p.moderationStatus = 'ACCEPTED' and " +
      " (lower(p.title) like concat(concat('%', lower(?1)),'%') or lower(p.text) like concat(concat('%', lower(?1)),'%')) ")
  Page<Post> searchPost(String query, Pageable page);

  @Query(value = "select * from posts where is_active = true and moderation_status = 'ACCEPTED' and cast(time as date) = cast(?1 as date)",
      nativeQuery = true)
  Page<Post> getPostByTimeCreate(LocalDate date, Pageable page);

  @Query(value = "select p from Post p where p.active = true and p.timeCreate <= current_date and p.moderationStatus = 'ACCEPTED' and ?1 in (select tg.name from p.tags tg)")
  Page<Post> getPostByTag(String tag, Pageable page);

  @Query(value = "select cast(cast(p.time as date) as char) as time, COUNT(p.id) as count\n" +
      "FROM posts p\n" +
      "WHERE p.is_active = true\n" +
      "  AND p.moderation_status = 'ACCEPTED'\n" +
      "  AND p.time <= current_date\n" +
      "  and extract(YEAR from p.time) = ?1\n" +
      "group by p.time\n" +
      "order by extract(YEAR from p.time)", nativeQuery = true)
  List<Map<String, Object>> calendarPosts(double year);

  @Query(value = "select distinct extract(year from p.time)\n" +
      "from posts p\n" +
      "where p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time <= now()", nativeQuery = true)
  List<Integer> getYearPosts();

  @Query(value = "select p.id as objId,\n" +
      "             count(case when pv.value = 1 then 1 end)  likeCount,\n" +
      "             count(case when pv.value = -1 then 1 end)  dislikeCount,\n" +
      "             coalesce(t.cc, 0)                        commentCount\n" +
      "      from posts p\n" +
      "               left join (select pc.post_id, count(pc.id) cc from post_comments pc group by pc.post_id) t on p.id = t.post_id\n" +
      "               left join post_votes pv on p.id = pv.post_id\n" +
      "       where p.id in (?1)\n" +
      "      group by p.id, t.cc",
    nativeQuery = true)
  List<SocialInfo> getSocial(List<Integer> postIds);

  @Query(value = "select *\n" +
      "from posts\n" +
      "where user_id = ?1\n" +
      "    and (\n" +
      "        case when ?2 = 'inactive' then is_active = false\n" +
      "            when ?2 = 'pending' then is_active and moderation_status = 'NEW'\n" +
      "            when ?2 = 'declined' then is_active and  moderation_status = 'DECLINED'\n" +
      "            when ?2 = 'published' then is_active and moderation_status = 'ACCEPTED'\n" +
      "        end\n" +
      "    )", nativeQuery = true)
  Page<Post> getMyPost(int userId, String status, Pageable pageable);

  @Query(value = "select count(p.id) from Post p where p.moderationStatus = 'NEW' and p.moderator.id = ?1")
  Integer countPostsForModeration(int moderatorId);

  @Query(value = "select p from Post p where p.moderationStatus = ?1 and p.user.id = ?2 and p.active")
  Page<Post> getPostsModeration(String status, Integer userId, Pageable pageable);

  @Query(value = "with likes as (\n" +
      "    select pv.post_id, count(case when pv.value = 1 then 1 end)  as l_count,\n" +
      "    count(case when pv.value = -1 then 1 end) as disl_count\n" +
      "    from post_votes pv\n" +
      "    group by pv.post_id\n" +
      "    )\n" +
      "select count(p.id) posts_count,\n" +
      "       sum(p.view_count) views_count,\n" +
      "       sum(likes.disl_count) dislikes_count,\n" +
      "       sum(likes.l_count) likes_count,\n" +
      "       min(p.time) as first\n" +
      "from posts p\n" +
      "    left join likes on p.id = likes.post_id\n" +
      "where (?1 is null or p.user_id = cast(?1 as int)) \n" +
      "    and p.is_active\n" +
      "    and p.moderation_status = 'ACCEPTED'\n" +
      "    and p.time <= now()", nativeQuery = true)
  StatisticModel getStatistic(Integer userId);
}