package skill.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skill.project.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "select t.id, is_active, moderation_status, moderator_id, text, time, title, view_count, user_id\n" +
        "from ( select p.*, count(pc.id) c_pc, coalesce(pv_l.cnt_l, 0) c_l\n" +
        "        from posts p\n" +
        "        left join post_comments pc on p.id = pc.post_id\n" +
        "        left join (select pv1.post_id, count(pv1.id) cnt_l from post_votes pv1 where pv1.value = '1' group by pv1.post_id) pv_l on p.id = pv_l.post_id\n" +
        "        where p.is_active = true and p.moderation_status = 'ACCEPTED' and p.time <= now()\n" +
        "        group by p.id, pv_l.cnt_l\n" +
        "    )t order by ?1", nativeQuery = true)
    Page<Post> getPosts(String sort, Pageable page);
}
