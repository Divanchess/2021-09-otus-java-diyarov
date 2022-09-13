create sequence hibernate_sequence start with 1 increment by 1;

create table clients
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table addresses
(
    client bigint  not null,
    street varchar(250)
);

create table phones
(
    id   bigserial not null primary key,
    number varchar(20),
    client_id bigint references clients (id)
);
