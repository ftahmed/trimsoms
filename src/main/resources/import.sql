CREATE extension pgcrypto;

BEGIN;

-- User
INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('u1', 'User1', 'Ahmed', '2000-01-01', 'u1@ftahmed.me', '0123456789', '$2a$08$zO3pHtw.LFU6w18PAoOStu0tRuztdDeX7l2qyfsII.JyogbxKIw9a');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('up1', (TABLE salt)) WHERE username = 'u1';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('u2', 'User2', 'Ahmed', '2000-02-02', 'u2@ftahmed.me', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('up1', (TABLE salt)) WHERE username = 'u2';
INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('a1', 'Admin1', 'Ahmed', '2000-03-03', 'a1@ftahmed.me', '0123456780', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('up1', (TABLE salt)) WHERE username = 'a1';
-- select * from "user";

-- Role
INSERT INTO role (role_name) VALUES ('USER');
INSERT INTO role (role_name) VALUES ('ADMIN');
INSERT INTO role (role_name) VALUES ('PUBLIC');
-- select * from "role";

-- UserRole ManyToMany relation
--WITH u1 AS (SELECT id FROM "user" WHERE username='u1'), r1 AS (SELECT id FROM role WHERE role_name='USER') INSERT INTO user_role (user_id, role_id) select (table u1), (table r1);
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='u1'), (SELECT id FROM role WHERE role_name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='u1'), (SELECT id FROM role WHERE role_name='ADMIN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='u2'), (SELECT id FROM role WHERE role_name='USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='a1'), (SELECT id FROM role WHERE role_name='ADMIN'));
-- SELECT username, first_name, role_name FROM "user_role", "user", "role" WHERE user_id="user".id AND role_id="role".id;

-- Part
INSERT INTO part (part_name) VALUES ('Adorn');
INSERT INTO part (part_name) VALUES ('Backpart');
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
INSERT INTO part (part_name) VALUES ('Insert');
INSERT INTO part (part_name) VALUES ('Lining');
INSERT INTO part (part_name) VALUES ('Lining Inside');
INSERT INTO part (part_name) VALUES ('Mesh Lining');
INSERT INTO part (part_name) VALUES ('Outer layer');
INSERT INTO part (part_name) VALUES ('Padding');
INSERT INTO part (part_name) VALUES ('Padding: Vest');
INSERT INTO part (part_name) VALUES ('Padding: Side piece, lower sleeve, collar');
INSERT INTO part (part_name) VALUES ('Padding Side section');
INSERT INTO part (part_name) VALUES ('Petticoat');
INSERT INTO part (part_name) VALUES ('Placket');
INSERT INTO part (part_name) VALUES ('Pocket');
INSERT INTO part (part_name) VALUES ('Shellfabric');
INSERT INTO part (part_name) VALUES ('Side section');
INSERT INTO part (part_name) VALUES ('Sleeve');
INSERT INTO part (part_name) VALUES ('Sleeve cuff');
INSERT INTO part (part_name) VALUES ('Sleeve lining');
INSERT INTO part (part_name) VALUES ('Sympatex-liner');
INSERT INTO part (part_name) VALUES ('Underprining');
INSERT INTO part (part_name) VALUES ('Spper Part');
INSERT INTO part (part_name) VALUES ('Vest');
INSERT INTO part (part_name) VALUES ('Yoke');
-- select * from "part";

-- Composition Item
INSERT INTO composition_item (ci_name) VALUES ('Acetate');
INSERT INTO composition_item (ci_name) VALUES ('Acrylic');
INSERT INTO composition_item (ci_name) VALUES ('Alpaca');
INSERT INTO composition_item (ci_name) VALUES ('Angora');
INSERT INTO composition_item (ci_name) VALUES ('Badger');
INSERT INTO composition_item (ci_name) VALUES ('Blue Fox');
INSERT INTO composition_item (ci_name) VALUES ('Bluefrost Fox');
INSERT INTO composition_item (ci_name) VALUES ('Buffalo Hide');
INSERT INTO composition_item (ci_name) VALUES ('Calfskin');
INSERT INTO composition_item (ci_name) VALUES ('Calfskin Nappa');
INSERT INTO composition_item (ci_name) VALUES ('Calfskin Suede');
INSERT INTO composition_item (ci_name) VALUES ('Camel');
INSERT INTO composition_item (ci_name) VALUES ('Cashgora');
INSERT INTO composition_item (ci_name) VALUES ('Cashmere');
INSERT INTO composition_item (ci_name) VALUES ('Coir');
INSERT INTO composition_item (ci_name) VALUES ('Cotton');
INSERT INTO composition_item (ci_name) VALUES ('Cow Hair');
INSERT INTO composition_item (ci_name) VALUES ('Cowhide');
INSERT INTO composition_item (ci_name) VALUES ('Cupro');
INSERT INTO composition_item (ci_name) VALUES ('Down');
INSERT INTO composition_item (ci_name) VALUES ('Elastane');
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
INSERT INTO composition_item (ci_name) VALUES ('Lambskin Nappa');
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
INSERT INTO composition_item (ci_name) VALUES ('Polyester');
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
-- select * from composition_item;

-- Brand
INSERT INTO brand (id, brand_name) VALUES (1, 'Betty Barclay');
INSERT INTO brand (id, brand_name) VALUES (2, 'Vera Mont');
INSERT INTO brand (id, brand_name) VALUES (3, 'Gil Bret');
INSERT INTO brand (id, brand_name) VALUES (4, 'Cartoon');
INSERT INTO brand (id, brand_name) VALUES (5, 'So Cossy');
INSERT INTO brand (id, brand_name) VALUES (6, 'Saint Jacques');
INSERT INTO brand (id, brand_name) VALUES (7, 'Betty & Co');
INSERT INTO brand (id, brand_name) VALUES (9, 'Amber & June');
-- select * from brand;

-- Vendor
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('213970', 'GMS Composite Knitting Ind. Ltd.');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('214020', 'Dipta Garments Ltd.');
-- select * from vendor;

-- Country
INSERT INTO country (code, "name") VALUES ('BD', 'Bangladesh');
INSERT INTO country (code, "name") VALUES ('DE', 'Germany');
-- select * from country;

-- Address
INSERT INTO address ("name", contact, email, phone, company, address1, city, postcode, country_code) VALUES ('Tanvir', 'Tanvir Ahmed', 't1@ftahmed.me', '0123456789', 'My Company', 'Uttara', 'Dhaka', '1230', 'BD');
INSERT INTO address ("name", contact, email, phone, company, address1, city, postcode, country_code) VALUES ('Sadat', 'K A Sadat', 's1@ftahmed.me', '0987654321', 'Retech IOT', 'Siddherswari', 'Dhaka', '1200', 'BD');
-- select * from address;

-- VendorAddress ManyToOne relation
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='213970'), a1 AS (SELECT id FROM address WHERE name='Tanvir') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
-- SELECT vendor_code, vendor_name, "name", contact, company FROM "vendor_addresses", "vendor", "address" WHERE vendor_id=vendor.id AND address_id=address.id;

COMMIT;

-- Orders
INSERT INTO public."order" (id, pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, paart, paart_bez, druckdatum, prod_description, druckmenge, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES (1, 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.597639+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.597639+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 61, '231', '36', '38', '42', '10', '8', '细码', '36', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO public."order" (id, pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, paart, paart_bez, druckdatum, prod_description, druckmenge, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES (2, 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.662559+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.662559+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 92, '231', '38', '40', '44', '12', '10', '中码', '38', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO public."order" (id, pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, paart, paart_bez, druckdatum, prod_description, druckmenge, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES (3, 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.673263+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.673263+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 112, '231', '40', '42', '46', '14', '12', '中码', '40', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO public."order" (id, pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, paart, paart_bez, druckdatum, prod_description, druckmenge, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES (4, 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.683876+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.683876+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 136, '231', '42', '44', '48', '16', '14', '大 码', '42', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO public."order" (id, pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, paart, paart_bez, druckdatum, prod_description, druckmenge, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES (5, 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.695482+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.695482+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 120, '231', '44', '46', '50', '18', '16', '大 码', '44', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO public."order" (id, pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, paart, paart_bez, druckdatum, prod_description, druckmenge, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES (6, 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.707906+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.707906+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 96, '231', '46', '48', '52', '20', '18', '加大 码', '46', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO public."order" (id, pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, paart, paart_bez, druckdatum, prod_description, druckmenge, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES (7, 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.726067+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.726067+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 33, '231', '48', '50', '54', '22', '20', ' 加大 码', '48', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
-- rder_id_seq
SELECT pg_catalog.setval('public.order_id_seq', 7, true);
