-- docker postgresql
-- $ docker run --name pg_db1 -d -e POSTGRES_PASSWORD=password -p 5432:5432 -v pg_db1:/var/lig/postgresql/data postgres:14
-- $ docker exec -ti pg_db1 psql -U postgres

-- before running the app
\c postgres
-- drop database mydb;
create database mydb owner myuser;
\c mydb 

INSERT INTO public."user" (username, first_name, last_name, dob, email, phone_number, password) 
  VALUES ('u1', 'User1', 'Ahmed', '2000-01-01', 'u1@ftahmed.me', '0123456789', '$2a$08$zO3pHtw.LFU6w18PAoOStu0tRuztdDeX7l2qyfsII.JyogbxKIw9a');
INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('u1', 'User1', 'Ahmed', '2000-01-01', 'u1@ftahmed.me', '0123456789', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('up1', (TABLE salt)) WHERE username = 'u1';
select * from "user";

INSERT INTO public.role (role_name) VALUES ('USER');
select * from role;

WITH u1 AS (SELECT id FROM "user" WHERE username='u1'), r1 AS (SELECT id FROM role WHERE role_name='USER') 
  INSERT INTO user_role (user_id, role_id) select (table u1), (table r1);
SELECT username, first_name, role_name FROM "user_role", "user", "role" WHERE user_id="user".id AND role_id=role.id;

INSERT INTO public.part (part_name) VALUES ('Adorn');
select * from part;

INSERT INTO public.composition_item (ci_name) VALUES ('Acetate');
select * from composition_item;

select * from brand;

select distinct pakokey_vp as brand, pakokey_saison as season, pakonr as ponr, papos_vkanr as article_nr, "type", pbetrnr as vendor_id, pbetrname1 as vendor_name, status from "order";
select * from "order";
select * from "order" where pakokey_vp is null;
delete from "order" where pakokey_vp is null;

select * from vendor;

INSERT INTO country (code, "name") VALUES ('BD', 'Bangladesh');
select * from country;

INSERT INTO address ("name", contact, email, phone, company, address1, city, postcode, country_code) VALUES ('tanvir', 'Tanvir Ahmed', 't1@ftahmed.me', '0123456789', 'My Company', 'Uttara', 'Dhaka', '1230', 'BD');
select * from address;

WITH v1 AS (SELECT id FROM "vendor" WHERE vendor_code='213970'), a1 AS (SELECT id FROM address WHERE name='tanvir') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
SELECT vendor_code, vendor_name, "name", contact, company FROM "vendor_addresses", "vendor", "address" WHERE vendor_id=vendor.id AND address_id=address.id;