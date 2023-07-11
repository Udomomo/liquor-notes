create table if not exists users (
    id varchar(24) primary key,
    username varchar(50) unique not null,
    password varchar(50) not null,
    email varchar(100) unique not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists reviews (
    id varchar(24) primary key,
    user_id bigint not null,
    title varchar(100) not null,
    content varchar(1000),
    stars decimal not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists tags (
    id varchar(24) primary key,
    name varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists review_tags (
    id varchar(24) primary key,
    review_id bigint not null,
    tag_id bigint not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    index idx_review_tags_review_id (review_id),
    index idx_review_tags_tag_id (tag_id)
);
