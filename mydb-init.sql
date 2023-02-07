-- docker postgresql
-- $ docker run --name pg_db1 -d -e POSTGRES_PASSWORD=password -p 5432:5432 -v pg_db1:/var/lig/postgresql/data postgres:14
-- $ docker exec -ti pg_db1 psql -U postgres

-- before running the app
\c postgres
-- drop database mydb;
create database mydb owner myuser;
\c mydb 

-- run the app
\d+
\d+ role
\d+ user
\d+ user_role

insert into role (role_name, enabled, date_created, last_updated) values 
  ('USER', true, now(), now()), 
  ('ADMIN', true, now(), now()),
  ('PUBLIC', true, now(), now());
select * from role;

insert into "user" (username, password, enabled, locked, email, first_name, last_name, dob, date_created, last_updated) values 
  ('u1', 'up1', true, false, 'u1@ftahmed.me', 'User1', 'Ahmed', (now()-'20 years'::interval)::date, now(), now()),
  ('u2', 'up2', true, false, 'u2@ftahmed.me', 'User2', 'Ahmed', (now()-'21 years'::interval)::date, now(), now()),
  ('a1', 'ap3', true, false, 'a1@ftahmed.me', 'Admin1', 'Ahmed', (now()-'22 years'::interval)::date, now(), now());
select * from "user";

create extension pgcrypto;
\df+ crypt

with salt as (select gen_salt('bf', 8) as salt) update "user" set password = crypt('up1', (table salt)) where username = 'u1';
select username, email, password from "user" where username = 'u1';
select password = crypt('up1', password) from "user" where username = 'u1';
select password = crypt('up1x', password) from "user" where username = 'u1';

with salt as (select gen_salt('bf', 8) as salt) update "user" set password = crypt('up2', (table salt)) where username = 'u2';
select username, email, password from "user" where username = 'u2';
select password = crypt('up2', password) from "user" where username = 'u2';
select password = crypt('up2x', password) from "user" where username = 'u2';

with salt as (select gen_salt('bf', 8) as salt) update "user" set password = crypt('ap1', (table salt)) where username = 'a1';
select username, email, password from "user" where username = 'a1';
select password = crypt('ap1', password) from "user" where username = 'a1';
select password = crypt('ap1x', password) from "user" where username = 'a1';

select username, email, password from "user";

insert into user_role (user_id, role_id) values 
  (1, 1), (1, 2),
  (2, 3),
  (3, 2);
select * from user_role ;
