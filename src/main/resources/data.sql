insert into users (username, password, nickname) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin');
insert into users (username, password, nickname) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user');


insert into authority (authority_name, user_id) values ('ROLE_USER', 1);
insert into authority (authority_name, user_id) values ('ROLE_USER', 2);
insert into authority (authority_name, user_id) values ('ROLE_ADMIN', 1);
