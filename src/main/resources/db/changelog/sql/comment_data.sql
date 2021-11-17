# post 1
select @idPost := id  from posts where title = 'post 1';
select @idUser := id from users where email = 'user_3@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now() - interval 1 hour, 'comment 1 for post post 1');

select @idUser := id from users where email = 'user_4@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now(), 'comment 2 for post post 1');


# post 3
select @idPost := id from posts where title = 'post 3';
select @idUser := id from users where email = 'user_3@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now() - interval 4 hour, 'comment 1 for post post 3');

select @idUser := id from users where email = 'user_4@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now() - interval 2 hour, 'comment 2 for post post 3');

select @idUser := id from users where email = 'user_2@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now(), 'comment 3 for post post 3');


# post 6
select @idPost := id from posts where title = 'post 6';

select @idUser := id from users where email = 'user_2@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now() - interval 5 hour, 'comment 3 for post post_6');

select @idUser := id from users where email = 'user_1@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now() - interval 4 hour, 'comment 1 for post post_6');

select @idUser := id from users where email = 'user_4@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now() - interval 2 hour, 'comment 2 for post post_6');

select @idUser := id from users where email = 'user_2@mail.ru';
insert into post_comments (post_id, user_id, time, text) value (@idPost, @idUser, now(), 'comment 4 for post post_6');

