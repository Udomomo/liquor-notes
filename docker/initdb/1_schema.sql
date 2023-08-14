create table if not exists users (
    id varchar(26) primary key,
    username varchar(50) unique not null,
    password varchar(50) not null,
    email varchar(100) unique not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists reviews (
    id varchar(26) primary key,
    user_id varchar(26) not null,
    title varchar(100) not null,
    content varchar(1000),
    star decimal not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists tags (
    id varchar(26) primary key,
    name varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    index idx_tags_name (name)
);

create table if not exists review_tag_mappings (
    review_id varchar(26) not null,
    tag_id varchar(26) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key(review_id, tag_id)
);
