
insert into roles (name) values ('ROLE_USER'), ('ROLE_ADMIN'), ('SOMETHING');

insert into users (name, password, email, age) values ('admin', '$2y$12$gAVSu1d20zzRLNifOWHyt.kbu2nXSQpQiIPy/MkwZILUKggZXmJUy', 'admin', 111), ('user', '$2y$12$gAVSu1d20zzRLNifOWHyt.kbu2nXSQpQiIPy/MkwZILUKggZXmJUy', 'user', 111) ;

insert into users_roles (user_id, role_id) values (1, 1), (1, 2), (2, 1);
