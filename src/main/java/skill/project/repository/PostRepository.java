package skill.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.Post;

import java.time.LocalDate;

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

  @Query(value = " from Post where active=true and timeCreate >= current_date and moderationStatus = 'ACCEPTED' and " +
      " (lower(title) like concat(concat('%', lower(?1)),'%') or lower(text) like concat(concat('%', lower(?1)),'%')) " +
      "  order by timeCreate desc")
  Page<Post> searchPost(String query, Pageable page);

  @Query(value = "select * from post where is_active = true and moderation_status = 'ACCEPTED' and cast(time as date) = cast(?1 as date)",
      nativeQuery = true)
  Page<Post> getPostByTimeCreate(LocalDate date, Pageable page);

  @Query(value = "from Post where active=true and timeCreate >= current_date and moderationStatus = 'ACCEPTED' and ?1 in (tags)")
  Page<Post> getPostByTag(String tag, Pageable page);

}
