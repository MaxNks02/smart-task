CREATE TABLE IF NOT EXISTS city
(
    id         serial primary key,
    code       bigint not null ,
    name       varchar(120) not null ,
    created_at varchar(50)
);