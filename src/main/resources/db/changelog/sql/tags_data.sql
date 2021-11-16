select @idPost := id  from posts where title = 'post 1';
select @idTag := id from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

DO $$
DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 1';
select id into idTag from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;

DO $$
DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 2';
select id into idTag from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'animal';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;


DO $$
    DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 3';
select id into idTag from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'weather';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;

DO $$
    DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 4';
select id into idTag from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag 86';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;

DO $$
    DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 5';
select id into idTag from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag 86';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;

DO $$
    DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 6';
select id into idTag from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;

DO $$
    DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 7';
select id into idTag from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'sweets';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'weather';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;

DO $$
    DECLARE
    idPost int;
    idTag int;
BEGIN
select id into idPost from posts where title = 'post 8';
select id into idTag from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;

DO $$
    DECLARE
    idPost int;
idTag int;
BEGIN
select id into idPost from posts where title = 'post 9';
select id into idTag from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (idPost, idTag);

select id into idTag from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (idPost, idTag);
END$$;