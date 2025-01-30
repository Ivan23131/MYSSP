create table t_authority(
    id serial primary key,
    c_authority varchar not null unique
);

create table t_user_authority(
    id serial primary key,
    id_user int not null references t_user(id),
    id_authority int not null references t_authority(id)
);