insert into clients (name) values ('Client1');
insert into clients (name) values ('Client2');
insert into clients (name) values ('Client3');
insert into clients (name) values ('Client4');
insert into clients (name) values ('Client5');

insert into addresses (street, client) values ('Street1', 1);
insert into addresses (street, client) values ('Street2', 2);
insert into addresses (street, client) values ('Street3', 3);
insert into addresses (street, client) values ('Street4', 4);
insert into addresses (street, client) values ('Street5', 5);

insert into phones (number, client_id) values ('12345-1', 1);
insert into phones (number, client_id) values ('12345-2', 2);
insert into phones (number, client_id) values ('12345-3', 3);
insert into phones (number, client_id) values ('12345-4', 4);
insert into phones (number, client_id) values ('12345-5', 5);
insert into phones (number, client_id) values ('12345-6', 1);
insert into phones (number, client_id) values ('12345-7', 1);
