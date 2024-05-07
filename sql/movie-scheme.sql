create table movie
(
    id     serial primary key,
    name   varchar not null,
    year   integer not null,
    genre  varchar not null,
    rating numeric(4, 2),
    count  integer not null
);

create table actor
(
    id       serial primary key,
    name     varchar not null,
    surname  varchar not null,
    movie_id integer references movie (id)
);

create table director
(
    id       serial primary key,
    name     varchar not null,
    surname  varchar not null,
    movie_id integer references movie (id)
);

create table users
(
    id       serial primary key,
    login    text not null,
    password text not null
);

create table roles
(
    id   serial primary key,
    name text not null
);

create table user_roles
(
    id      serial primary key,
    user_id integer references users (id),
    role_id integer references roles (id)
);

