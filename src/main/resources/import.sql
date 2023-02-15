BEGIN;

INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('u1', 'User1', 'Ahmed', '2000-01-01', 'u1@ftahmed.me', '0123456789', '$2a$08$zO3pHtw.LFU6w18PAoOStu0tRuztdDeX7l2qyfsII.JyogbxKIw9a');
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('u2', 'User2', 'Ahmed', '2000-02-02', 'u2@ftahmed.me', '$2a$08$rrQ40ptPiNerrVJl9Ku9/O3uAtcf1fiq92.iP/958Uo5quFxacvlC');
INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('a1', 'Admin1', 'Ahmed', '2000-03-03', 'a1@ftahmed.me', '0123456780', '$2a$08$EAIZ66mh4MGpOW/oQN3SgehC..2hiMLGXY6r3pR37bbFO0EdVXDB6');
-- select * from "user";

INSERT INTO role (role_name) VALUES ('USER');
INSERT INTO role (role_name) VALUES ('ADMIN');
INSERT INTO role (role_name) VALUES ('PUBLIC');
-- select * from "role";

--WITH u1 AS (SELECT id FROM "user" WHERE username='u1'), r1 AS (SELECT id FROM role WHERE role_name='USER') INSERT INTO user_role (user_id, role_id) select (table u1), (table r1);
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='u1'), (SELECT id FROM role WHERE role_name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='u1'), (SELECT id FROM role WHERE role_name='ADMIN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='u2'), (SELECT id FROM role WHERE role_name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='a1'), (SELECT id FROM role WHERE role_name='ADMIN'));
-- SELECT username, first_name, role_name FROM "user_role", "user", "role" WHERE user_id="user".id AND role_id="role".id;

INSERT INTO part (part_name) VALUES ('Adorn');
INSERT INTO part (part_name) VALUES ('Backpart ');
INSERT INTO part (part_name) VALUES ('Bottom hem');
INSERT INTO part (part_name) VALUES ('Coating');
INSERT INTO part (part_name) VALUES ('Coller');
INSERT INTO part (part_name) VALUES ('Cuff');
INSERT INTO part (part_name) VALUES ('Embroidary');
INSERT INTO part (part_name) VALUES ('Facing');
INSERT INTO part (part_name) VALUES ('Frontpart');
INSERT INTO part (part_name) VALUES ('Fur');
INSERT INTO part (part_name) VALUES ('Fur-Trimming');
INSERT INTO part (part_name) VALUES ('Hood');
INSERT INTO part (part_name) VALUES ('Hoodlining');
INSERT INTO part (part_name) VALUES ('Inner layer ');
INSERT INTO part (part_name) VALUES ('Insert ');
INSERT INTO part (part_name) VALUES ('Lining');
INSERT INTO part (part_name) VALUES ('Lining Inside');
INSERT INTO part (part_name) VALUES ('Mesh Lining');
INSERT INTO part (part_name) VALUES ('Outer layer');
INSERT INTO part (part_name) VALUES ('Padding');
INSERT INTO part (part_name) VALUES ('Padding: Vest');
INSERT INTO part (part_name) VALUES ('Padding: Side piece, lower sleeve, collar ');
INSERT INTO part (part_name) VALUES ('Padding Side section ');
INSERT INTO part (part_name) VALUES ('Petticoat ');
INSERT INTO part (part_name) VALUES ('Placket');
INSERT INTO part (part_name) VALUES ('Pocket ');
INSERT INTO part (part_name) VALUES ('Shellfabric');
INSERT INTO part (part_name) VALUES ('Side section ');
INSERT INTO part (part_name) VALUES ('Sleeve');
INSERT INTO part (part_name) VALUES ('Sleeve cuff');
INSERT INTO part (part_name) VALUES ('Sleeve lining');
INSERT INTO part (part_name) VALUES ('Sympatex-liner');
INSERT INTO part (part_name) VALUES ('Underprining');
INSERT INTO part (part_name) VALUES ('Spper Part ');
INSERT INTO part (part_name) VALUES ('Vest');
INSERT INTO part (part_name) VALUES ('Yoke');
-- select * from "part";

INSERT INTO composition_item (ci_name) VALUES ('Acetate');
INSERT INTO composition_item (ci_name) VALUES ('Acrylic');
INSERT INTO composition_item (ci_name) VALUES ('Alpaca ');
INSERT INTO composition_item (ci_name) VALUES ('Angora');
INSERT INTO composition_item (ci_name) VALUES ('Badger');
INSERT INTO composition_item (ci_name) VALUES ('Blue Fox');
INSERT INTO composition_item (ci_name) VALUES ('Bluefrost Fox ');
INSERT INTO composition_item (ci_name) VALUES ('Buffalo Hide');
INSERT INTO composition_item (ci_name) VALUES ('Calfskin');
INSERT INTO composition_item (ci_name) VALUES ('Calfskin Nappa ');
INSERT INTO composition_item (ci_name) VALUES ('Calfskin Suede');
INSERT INTO composition_item (ci_name) VALUES ('Camel');
INSERT INTO composition_item (ci_name) VALUES ('Cashgora');
INSERT INTO composition_item (ci_name) VALUES ('Cashmere');
INSERT INTO composition_item (ci_name) VALUES ('Coir');
INSERT INTO composition_item (ci_name) VALUES ('Cotton ');
INSERT INTO composition_item (ci_name) VALUES ('Cow Hair');
INSERT INTO composition_item (ci_name) VALUES ('Cowhide');
INSERT INTO composition_item (ci_name) VALUES ('Cupro');
INSERT INTO composition_item (ci_name) VALUES ('Down');
INSERT INTO composition_item (ci_name) VALUES ('Elastane ');
INSERT INTO composition_item (ci_name) VALUES ('Elastodiene');
INSERT INTO composition_item (ci_name) VALUES ('Elastomultiester');
INSERT INTO composition_item (ci_name) VALUES ('Feathers');
INSERT INTO composition_item (ci_name) VALUES ('Fox');
INSERT INTO composition_item (ci_name) VALUES ('Goat Hair');
INSERT INTO composition_item (ci_name) VALUES ('Goat nubuck Lather');
INSERT INTO composition_item (ci_name) VALUES ('Goat skin');
INSERT INTO composition_item (ci_name) VALUES ('Goatskin Nappa');
INSERT INTO composition_item (ci_name) VALUES ('Goatskin Suede');
INSERT INTO composition_item (ci_name) VALUES ('Guanaco');
INSERT INTO composition_item (ci_name) VALUES ('Hemp');
INSERT INTO composition_item (ci_name) VALUES ('Henequen');
INSERT INTO composition_item (ci_name) VALUES ('Horse Hair');
INSERT INTO composition_item (ci_name) VALUES ('Jute');
INSERT INTO composition_item (ci_name) VALUES ('Kapok');
INSERT INTO composition_item (ci_name) VALUES ('Kenf');
INSERT INTO composition_item (ci_name) VALUES ('Lamfur');
INSERT INTO composition_item (ci_name) VALUES ('Lambskin');
INSERT INTO composition_item (ci_name) VALUES ('Lambskin Nappa ');
INSERT INTO composition_item (ci_name) VALUES ('Lambskin Suede');
INSERT INTO composition_item (ci_name) VALUES ('Leather');
INSERT INTO composition_item (ci_name) VALUES ('Linen');
INSERT INTO composition_item (ci_name) VALUES ('Llama');
INSERT INTO composition_item (ci_name) VALUES ('Lyocell');
INSERT INTO composition_item (ci_name) VALUES ('Merino Lamb, nappa finish');
INSERT INTO composition_item (ci_name) VALUES ('Metal');
INSERT INTO composition_item (ci_name) VALUES ('Mink');
INSERT INTO composition_item (ci_name) VALUES ('Mixed Fibres');
INSERT INTO composition_item (ci_name) VALUES ('Modacrylic');
INSERT INTO composition_item (ci_name) VALUES ('Modal');
INSERT INTO composition_item (ci_name) VALUES ('Mohair');
INSERT INTO composition_item (ci_name) VALUES ('Muskrat');
INSERT INTO composition_item (ci_name) VALUES ('Nature straw');
INSERT INTO composition_item (ci_name) VALUES ('Ostrich feather');
INSERT INTO composition_item (ci_name) VALUES ('Paper');
INSERT INTO composition_item (ci_name) VALUES ('Persian astrakan');
INSERT INTO composition_item (ci_name) VALUES ('Pigskin');
INSERT INTO composition_item (ci_name) VALUES ('Pigskin nappa');
INSERT INTO composition_item (ci_name) VALUES ('Pigskin suede');
INSERT INTO composition_item (ci_name) VALUES ('Polyamide');
INSERT INTO composition_item (ci_name) VALUES ('Polyamide (recycled)');
INSERT INTO composition_item (ci_name) VALUES ('Polychloroprene');
INSERT INTO composition_item (ci_name) VALUES ('Polyester ');
INSERT INTO composition_item (ci_name) VALUES ('Polyester metallised');
INSERT INTO composition_item (ci_name) VALUES ('Polyethylene');
INSERT INTO composition_item (ci_name) VALUES ('Polypropylene');
INSERT INTO composition_item (ci_name) VALUES ('Polyurethane');
INSERT INTO composition_item (ci_name) VALUES ('Polyvinyl chloride');
INSERT INTO composition_item (ci_name) VALUES ('Rabbit');
INSERT INTO composition_item (ci_name) VALUES ('Raccoon');
INSERT INTO composition_item (ci_name) VALUES ('Ramie');
INSERT INTO composition_item (ci_name) VALUES ('Recycled Polyester');
INSERT INTO composition_item (ci_name) VALUES ('Red Fox');
INSERT INTO composition_item (ci_name) VALUES ('Rex Rabbit');
INSERT INTO composition_item (ci_name) VALUES ('Sheepskin');
INSERT INTO composition_item (ci_name) VALUES ('Silk');
INSERT INTO composition_item (ci_name) VALUES ('Silver Fox');
INSERT INTO composition_item (ci_name) VALUES ('Squirrel');
INSERT INTO composition_item (ci_name) VALUES ('Tibetan lamb');
INSERT INTO composition_item (ci_name) VALUES ('Toscana lamb');
INSERT INTO composition_item (ci_name) VALUES ('Triacetate');
INSERT INTO composition_item (ci_name) VALUES ('Tussah silk');
INSERT INTO composition_item (ci_name) VALUES ('Unspecified composition');
INSERT INTO composition_item (ci_name) VALUES ('Vicuna');
INSERT INTO composition_item (ci_name) VALUES ('Virgin wool');
INSERT INTO composition_item (ci_name) VALUES ('Viscose');
INSERT INTO composition_item (ci_name) VALUES ('Viscose (made from bamboo pulp)');
INSERT INTO composition_item (ci_name) VALUES ('Wool');
INSERT INTO composition_item (ci_name) VALUES ('Yak');
-- select * from "composition_item";
 	
INSERT INTO brand (id, brand_name) VALUES (1, 'Betty Barclay');
INSERT INTO brand (id, brand_name) VALUES (2, 'Vera Mont');
INSERT INTO brand (id, brand_name) VALUES (3, 'Gil Bret');
INSERT INTO brand (id, brand_name) VALUES (4, 'Cartoon');
INSERT INTO brand (id, brand_name) VALUES (5, 'So Cossy');
INSERT INTO brand (id, brand_name) VALUES (6, 'Saint Jacques');
INSERT INTO brand (id, brand_name) VALUES (7, 'Betty & Co');
INSERT INTO brand (id, brand_name) VALUES (9, 'Amber & June');

-- Oredr status
-- NEW
-- CALLED_OFF
-- ALL

-- Vendor
-- 213970 - GMS Composite Knitting Ind. Ltd.
-- 214020 - Dipta Garments Ltd.

COMMIT;