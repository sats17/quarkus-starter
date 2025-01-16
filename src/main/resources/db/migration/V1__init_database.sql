create sequence location_seq start with 1 increment by 50;

create table location
(
    id    bigint DEFAULT nextval('location_seq') not null,
    city  varchar                                 not null,
    lat varchar                                 not null,
    lon varchar                                 not null,
    primary key (id)
);

insert into location(city, lat, lon)
values ('mumbai', '22', '12'),
       ('navi-mumbai', '22', '12');