# post 1
select @idPost := id  from posts where title = 'post 1';
select @idTag := id from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);


# post 2
select @idPost := id from posts where title = 'post 2';
select @idTag := id from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'animal';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

# post 3
select @idPost := id from posts where title = 'post 3';
select @idTag := id from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'weather';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

# post 4
select @idPost := id from posts where title = 'post 4';
select @idTag := id from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag 86';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);


# post 5
select @idPost := id from posts where title = 'post 5';
select @idTag := id from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag 86';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

# post 6
select @idPost := id from posts where title = 'post 6';
select @idTag := id from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

# post 7
select @idPost := id from posts where title = 'post 7';
select @idTag := id from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'sweets';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'weather';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

# post 8
select @idPost := id from posts where title = 'post 8';
select @idTag := id from tags where name = 'tag22';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

# post 9
select @idPost := id from posts where title = 'post 9';
select @idTag := id from tags where name = 'tag 66';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'tag1';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);

select @idTag := id from tags where name = 'news';
insert into tag2post (post_id, tag_id) values (@idPost, @idTag);