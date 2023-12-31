create table if not exists users (
    id varchar(26) primary key,
    username varchar(50) unique not null,
    password varchar(128) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists reviews (
    id varchar(26) primary key,
    user_id varchar(26) not null,
    title varchar(100) not null,
    content varchar(1000),
    star decimal(3, 1) not null,
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

create table if not exists locations (
    id varchar(26) primary key,
    name varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    index idx_location_name (name)
);

create table if not exists review_location_mappings (
    review_id varchar(26) not null,
    location_id varchar(26) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key(review_id, location_id)
);
