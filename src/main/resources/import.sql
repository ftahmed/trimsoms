CREATE extension pgcrypto;

BEGIN;

-- User

INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('a1', 'GMS', 'Admin', '2000-01-01', 'a1@gmstrims.co', '0123456780', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('passa1', (TABLE salt)) WHERE username = 'a1';
INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('t1', 'GMS', 'Trims', '2000-01-01', 't1@gmstrims.co', '0123456789', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('passt1', (TABLE salt)) WHERE username = 't1';
INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('v1', 'GMS', 'Vendor', '2000-01-01', 'v1@gmstrims.co', '0987654321', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('passv1', (TABLE salt)) WHERE username = 'v1';

INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('gmsadmin', 'GMS', 'Admin', '2000-01-01', 'gmsadmin@gmstrims.co', '0123456780', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('passa1', (TABLE salt)) WHERE username = 'gmsadmin';
INSERT INTO "user" (username, first_name, last_name, dob, email, phone_number, password) VALUES ('gmstrims', 'GMS', 'Trims', '2000-01-01', 'gmstrims@gmstrims.co', '0123456789', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('passt1', (TABLE salt)) WHERE username = 'gmstrims';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('saadatia', 'Saadatia', 'User', '2000-02-02', 'saadatia@gmstrims.co', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('pass214204', (TABLE salt)) WHERE username = 'saadatia';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('mascot', 'Mascot', 'User', '2000-02-02', 'mascot@gmstrims.co', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('pass214060', (TABLE salt)) WHERE username = 'mascot';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('floreal', 'Floreal', 'User', '2000-02-02', 'floreal@gmstrims.co', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('pass213535', (TABLE salt)) WHERE username = 'floreal';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('gmsc', 'GMS Composite', 'User', '2000-02-02', 'gmsc@gmstrims.co', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('pass213970', (TABLE salt)) WHERE username = 'gmsc';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('metro', 'Metro', 'User', '2000-02-02', 'metro@gmstrims.co', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('pass214088', (TABLE salt)) WHERE username = 'metro';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('aman', 'Aman Tex', 'User', '2000-02-02', 'aman@gmstrims.co', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('pass213764', (TABLE salt)) WHERE username = 'aman';
INSERT INTO "user" (username, first_name, last_name, dob, email, password) VALUES ('gmst', 'GMS Textiles', 'User', '2000-02-02', 'gmst@gmstrims.co', '');
WITH salt AS (SELECT gen_salt('bf', 8) AS salt) UPDATE "user" SET password = crypt('pass214240', (TABLE salt)) WHERE username = 'gmst';
-- select * from "user";

-- Role
INSERT INTO role (role_name) VALUES ('TRM');
INSERT INTO role (role_name) VALUES ('VEN');
INSERT INTO role (role_name) VALUES ('ADMIN');
-- select * from "role";

-- UserRole ManyToMany relation
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='a1'), (SELECT id FROM role WHERE role_name='ADMIN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='t1'), (SELECT id FROM role WHERE role_name='TRM'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='v1'), (SELECT id FROM role WHERE role_name='VEN'));

INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='gmsadmin'), (SELECT id FROM role WHERE role_name='ADMIN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='gmstrims'), (SELECT id FROM role WHERE role_name='TRM'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='saadatia'), (SELECT id FROM role WHERE role_name='VEN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='mascot'), (SELECT id FROM role WHERE role_name='VEN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='floreal'), (SELECT id FROM role WHERE role_name='VEN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='gmsc'), (SELECT id FROM role WHERE role_name='VEN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='metro'), (SELECT id FROM role WHERE role_name='VEN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='aman'), (SELECT id FROM role WHERE role_name='VEN'));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM "user" WHERE username='gmst'), (SELECT id FROM role WHERE role_name='VEN'));
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
INSERT INTO part (part_name) VALUES ('Inner layer');
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
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('000000', 'Test Vendor');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('214204', 'Saadatia Sweaters Ltd.');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('214060', 'Mascot Ltd. DMCC');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('213535', 'Floreal International Ltd.');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('213970', 'GMS Composite Knitting Ind Ltd.');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('214088', 'Metro Knitting & Dyeing Mills Ltd.');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('213764', 'Aman Tex Ltd.');
INSERT INTO vendor (vendor_code, vendor_name) VALUES ('214240', 'GMS Textiles Ltd.');

-- select * from vendor;
UPDATE "user" SET vendor_vendor_code = '000000' WHERE username = 'v1';
UPDATE "user" SET vendor_vendor_code = '214204' WHERE username = 'saadatia';
UPDATE "user" SET vendor_vendor_code = '214060' WHERE username = 'mascot';
UPDATE "user" SET vendor_vendor_code = '213535' WHERE username = 'floreal';
UPDATE "user" SET vendor_vendor_code = '213970' WHERE username = 'gmsc';
UPDATE "user" SET vendor_vendor_code = '214088' WHERE username = 'metro';
UPDATE "user" SET vendor_vendor_code = '213764' WHERE username = 'aman';
UPDATE "user" SET vendor_vendor_code = '214240' WHERE username = 'gmst';

-- Country
INSERT INTO country (code, "name") VALUES ('BD', 'Bangladesh');
INSERT INTO country (code, "name") VALUES ('DE', 'Germany');
-- select * from country;

-- Address
INSERT INTO address (address1, address2, address3, city, company, contact, name, phone, postcode, country_code) VALUES ('Diakhali,  Molla Bazar', ' Ashulia', 'Savar', 'Dhaka', 'Saadatia Sweaters Ltd.', 'Default', '214204', '8801958620904', '', 'BD');
INSERT INTO address (address1, address2, address3, city, company, contact, name, phone, postcode, country_code) VALUES ('146 Zirabo', 'Dewan Idris Road', 'Ashulia', 'Dhaka', 'Mascot Ltd. DMCC', 'Default', '214060', '880248953238', '', 'BD');
INSERT INTO address (address1, address2, address3, city, company, contact, name, phone, postcode, country_code) VALUES ('Road # 5, House # 343', ' DOHS', 'Baridhara DOHS', 'Dhaka', 'Floreal International Ltd.', 'Default', '213535', '8801713060648', '', 'BD');
INSERT INTO address (address1, address2, address3, city, company, contact, name, phone, postcode, country_code) VALUES ('Sardaganj', 'Kashimpur', '', 'Gazipur', 'GMS Composite Knitting Ind Ltd.', 'Default', '213970', '8801938912050', '1346', 'BD');
INSERT INTO address (address1, address2, address3, city, company, contact, name, phone, postcode, country_code) VALUES ('Ramarbag', 'Kutubpur', 'Fatullah', 'Narayangonj', 'Metro Knitting & Dyeing Mills Ltd.', 'Default', '214088', '88028833753', '1400', 'BD');
INSERT INTO address (address1, address2, address3, city, company, contact, name, phone, postcode, country_code) VALUES ('Boiragirchala', 'Sreepur', '', 'Gazipur', 'Aman Tex Ltd.', 'Default', '213764', '88027911016', '', 'BD');
INSERT INTO address (address1, address2, address3, city, company, contact, name, phone, postcode, country_code) VALUES ('Tansutrapur', 'Kaliakoir', '', 'Gazipur', 'GMS Textiles Ltd.', 'Default', '214240', '880277889313', '1750', 'BD');
-- select * from address;

-- VendorAddress ManyToOne relation
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='214204'), a1 AS (SELECT id FROM address WHERE name='214204') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='214060'), a1 AS (SELECT id FROM address WHERE name='214060') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='213535'), a1 AS (SELECT id FROM address WHERE name='213535') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='213970'), a1 AS (SELECT id FROM address WHERE name='213970') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='214088'), a1 AS (SELECT id FROM address WHERE name='214088') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='213764'), a1 AS (SELECT id FROM address WHERE name='213764') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
WITH v1 AS (SELECT id FROM vendor WHERE vendor_code='214240'), a1 AS (SELECT id FROM address WHERE name='214240') INSERT INTO vendor_addresses (vendor_id, address_id) select (table v1), (table a1);
-- SELECT vendor_code, vendor_name, "name", contact, company FROM "vendor_addresses", "vendor", "address" WHERE vendor_id=vendor.id AND address_id=address.id;

-- Invoice address
WITH a1 AS (SELECT id FROM address WHERE name='214204') UPDATE vendor SET invoice_address_id = (table a1) WHERE vendor_code='214204';
WITH a1 AS (SELECT id FROM address WHERE name='214060') UPDATE vendor SET invoice_address_id = (table a1) WHERE vendor_code='214060';
WITH a1 AS (SELECT id FROM address WHERE name='213535') UPDATE vendor SET invoice_address_id = (table a1) WHERE vendor_code='213535';
WITH a1 AS (SELECT id FROM address WHERE name='213970') UPDATE vendor SET invoice_address_id = (table a1) WHERE vendor_code='213970';
WITH a1 AS (SELECT id FROM address WHERE name='214088') UPDATE vendor SET invoice_address_id = (table a1) WHERE vendor_code='214088';
WITH a1 AS (SELECT id FROM address WHERE name='213764') UPDATE vendor SET invoice_address_id = (table a1) WHERE vendor_code='213764';
WITH a1 AS (SELECT id FROM address WHERE name='214240') UPDATE vendor SET invoice_address_id = (table a1) WHERE vendor_code='214240';

COMMIT;

BEGIN;

-- Orders
INSERT INTO "order" (pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, pa_art, pa_art_bez, druckdatum, prod_description, druckmenge, druckmenge_orig, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES ('Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.597639+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.597639+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 61,  61, '231', '36', '38', '42', '10', '8', '细码', '36', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO "order" (pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, pa_art, pa_art_bez, druckdatum, prod_description, druckmenge, druckmenge_orig, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES ('Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.662559+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.662559+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 92,  92, '231', '38', '40', '44', '12', '10', '中码', '38', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO "order" (pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, pa_art, pa_art_bez, druckdatum, prod_description, druckmenge, druckmenge_orig, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES ('Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.673263+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.673263+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 112, 112, '231', '40', '42', '46', '14', '12', '中码', '40', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO "order" (pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, pa_art, pa_art_bez, druckdatum, prod_description, druckmenge, druckmenge_orig, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES ('Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.683876+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.683876+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 136, 136, '231', '42', '44', '48', '16', '14', '大 码', '42', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO "order" (pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, pa_art, pa_art_bez, druckdatum, prod_description, druckmenge, druckmenge_orig, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES ('Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.695482+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.695482+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 120, 120, '231', '44', '46', '50', '18', '16', '大 码', '44', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO "order" (pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, pa_art, pa_art_bez, druckdatum, prod_description, druckmenge, druckmenge_orig, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES ('Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.707906+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.707906+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 96,  96, '231', '46', '48', '52', '20', '18', '加大 码', '46', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');
INSERT INTO "order" (pflegehinw01, pflegehinw02, pflegehinw03, pflegehinw04, pflegehinw05, pflegehinw06, pflegehinw07, pflegehinw08, pflegehinw09, pflegehinw10, pflegehinw11, pflegehinw12, pflegehinw13, pflegehinw14, pflegehinw15, pflegehinw16, pflegehinw17, pflegehinw18, pflegehinw19, pflegehinw20, pflegehinw21, pflegehinw22, pflegehinw23, pflegehinw24, pflegehinw25, pflegehinw26, pflegehinw27, pflegehinweis, pflegehinweis2, tkg_zusatz2, papos_vkabez, papos_vkaname, papos_vkanr, pakokey_vp, canummer, pflegesym, papos_farbbez, papos_farbnr, pakokey_firma, date_created, designedin, absender_ort, absender_name, absender_strasse, absender_www, pbetrmatch, pbetrname1, pakokey, papos_vkarkey, papos_vkarkey_vp, papos_vkarkey_firma, papos_vkarkey_saison, last_updated, madein, papos_matnr, status, type, peter_hahn_nr, pakonr, pa_art, pa_art_bez, druckdatum, prod_description, druckmenge, druckmenge_orig, pakokey_saison, pagr, grbez1, grbez2, grbez3, grbez4, grbez5, grbez6, grbez_ueb1, grbez_ueb2, grbez_ueb3, grbez_ueb4, grbez_ueb5, grbez_ueb6, pbetrnr) VALUES ('Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen', 'Please turn inside out and wash with mild detergent. Reshape after washing.', 'Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage.', 'Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado.', 'Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму', 'Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc.', 'Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru.', 'Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', '用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Bitte auf links drehen und mit Feinwaschmittel waschen, nach der Wäsche in Form ziehen, Please turn inside out and wash with mild detergent. Reshape after washing., Laver à l’envers et utiliser un produit pour linge délicat, étendre en forme après le lavage., Volver del revés y lavar con detergente para prendas delicadas y tirar en forma después del lavado., Выверните наизнанку, стирайте с использованием средств для стирки тонких тканей, после стирки придайте форму, Prosze odwrócic na lewa strone i prac w plynie do tkanin delikatnych, po wypraniu rozlozyc., Obratte naruby a vyperte jemným pracím prostredkem, po praní vytáhnete do tvaru., Kímélo mosószerrel a fonákján mossuk, mosás után pedig jól húzogassuk ki.', ' 用温和洗衣剂反面洗涤，洗涤后轻拉使其成形。', '', 'Shirt Maßtab', '', '26861007', '1', 'CA: 07113', '066104102085101', 'Gentian Violet', '6352', 'F1', '2023-02-18 18:39:17.726067+00', 'Designed in Germany', '69226 Nussloch - Germany', 'Betty Barclay Group GmbH & Co. KG', 'Heidelberger Str. 9-11', 'www.bettybarclay.com', 'GMS', 'GMS Composite Knitting Ind Ltd', 'F11231', 'F11231', '1', 'F1', '231', '2023-02-18 18:39:17.726067+00', ' ', '1007', 'New', 'SMS', ' ', '400160', '', '', NULL, 'Pullover, Sweater, пуловер', 33,  33, '231', '48', '50', '54', '22', '20', ' 加大 码', '48', 'E/F', 'I/RUS', 'GB', 'USA/CA', 'CHN', 'D/NL/CH/PL/CZ/HU', '213970');

-- Purchase Orders
INSERT INTO purchase_order (approval_date, article_number, brand, date_created, delivery_address, factory_name1, last_updated, layout_date, layout_file, order_by, po_number, printer_notes, reject_reason, season, status, total_qty, total_qty_orig, product, type, order_original_file, vendor_code, vendor_po) VALUES (NULL, '26861007', '1', '2023-02-20 06:07:56.419673+00', NULL, 'GMS Composite Knitting Ind Ltd', '2023-02-20 06:07:56.419673+00', NULL, NULL, NULL, '400160', NULL, NULL, '231', 'New', 650, 650, 'carelabel', 'SMS', 'gmsbeb-order-test-1.csv', '213970', NULL);
-- Purchase Orders Composition
INSERT INTO purchase_order_compositions (purchase_order_id, compositions) VALUES (1, 'part:Hood');
INSERT INTO purchase_order_compositions (purchase_order_id, compositions) VALUES (1, 'ci:Cowhide:80%');
INSERT INTO purchase_order_compositions (purchase_order_id, compositions) VALUES (1, 'ci:Cow Hair:20%');
INSERT INTO purchase_order_compositions (purchase_order_id, compositions) VALUES (1, 'part:Frontpart');
INSERT INTO purchase_order_compositions (purchase_order_id, compositions) VALUES (1, 'ci:Cotton:100%');

COMMIT;

BEGIN;

-- Ticket
INSERT INTO ticket (code, "name") VALUES ('5100001', 'Betty Barclay');
INSERT INTO ticket (code, "name") VALUES ('5100002', 'Betty Barclay');
INSERT INTO ticket (code, "name") VALUES ('5100005', 'Gil Bret');
INSERT INTO ticket (code, "name") VALUES ('5100006', 'Gil Bret');
INSERT INTO ticket (code, "name") VALUES ('5100010', 'Vera Mont');
INSERT INTO ticket (code, "name") VALUES ('5100017', 'Robe legere');
INSERT INTO ticket (code, "name") VALUES ('5100020', 'Robe Iegere');
INSERT INTO ticket (code, "name") VALUES ('5100021', 'Vera Mont');
INSERT INTO ticket (code, "name") VALUES ('5100040', 'Betty & Co Grey');
INSERT INTO ticket (code, "name") VALUES ('5100042', 'Betty & Co Grey');
INSERT INTO ticket (code, "name") VALUES ('5100043', 'Betty & Co White');
INSERT INTO ticket (code, "name") VALUES ('5100045', 'Vera Mont');
INSERT INTO ticket (code, "name") VALUES ('5100046', 'Vera Mont');
INSERT INTO ticket (code, "name") VALUES ('5100047', 'Betty & Co White');
INSERT INTO ticket (code, "name") VALUES ('5100048', 'Cartoon Daily');
INSERT INTO ticket (code, "name") VALUES ('5100050', 'PUBLIC');
INSERT INTO ticket (code, "name") VALUES ('5100051', 'PUBLIC');
INSERT INTO ticket (code, "name") VALUES ('5100052', 'Cartoon UPgreat');
INSERT INTO ticket (code, "name") VALUES ('5100053', 'Suddenly Princess');
INSERT INTO ticket (code, "name") VALUES ('5100054', 'Vera Mont - V');
INSERT INTO ticket (code, "name") VALUES ('5100055', 'Cartoon NOW');
INSERT INTO ticket (code, "name") VALUES ('5100056', 'Suddenly Princess');
INSERT INTO ticket (code, "name") VALUES ('5100057', 'Vera Mont - V');
INSERT INTO ticket (code, "name") VALUES ('5100058', 'Cartoon Amazing Outerwe@r');
INSERT INTO ticket (code, "name") VALUES ('5100059', 'Cartoon Outerwe@r');
INSERT INTO ticket (code, "name") VALUES ('5100060', 'Betty Barclay - Happy');
INSERT INTO ticket (code, "name") VALUES ('5100061', 'Cartoon Amazing');
INSERT INTO ticket (code, "name") VALUES ('5100062', 'Cartoon Daydream');
INSERT INTO ticket (code, "name") VALUES ('5100063', 'Amber & June');
INSERT INTO ticket (code, "name") VALUES ('5100064', 'Amber & June');
INSERT INTO ticket (code, "name") VALUES ('5100065', 'Cartoon Amazing');
INSERT INTO ticket (code, "name") VALUES ('5100066', 'So Cosy');
INSERT INTO ticket (code, "name") VALUES ('5100067', 'So Cosy');
INSERT INTO ticket (code, "name") VALUES ('5100068', 'Cartoon UPgreat');
INSERT INTO ticket (code, "name") VALUES ('5100069', 'Cartoon UPgreat');
INSERT INTO ticket (code, "name") VALUES ('6000000', 'SMS Tag');
INSERT INTO ticket (code, "name") VALUES ('', '');

COMMIT;

BEGIN;

INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty Barclay + so cosy + Gil Bret', '40-76-2908' , 'String' , 'Ribbon', '14.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Cartoon', '40-76-2991', 'Woven', 'Ribbon', '65.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Cartoon', '40-76-2992', 'String', 'Ribbon', '100.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty & Co Amber & June', '40-76-703', 'Seal', 'String for pricetag', '10.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty Barclay', '75-10-1000', 'Woven', 'Brandlabel', '40.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('So cosy', '75-10-5000', 'PFL', 'Brandlabel', '50.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Cartoon', '75-10-510', 'Woven', 'Brandlabel small', '85.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty & Co', '75-10-7009', 'Woven', '"FLAG ""grey"""', '60.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty & Co', '75-10-7015', 'Woven', '"FLAG ""white"""', '60.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty & CO', '75-10-7100', 'Woven', 'Brandlabel rectangle endfold', '50.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty & Co', '75-10-7101', 'Woven', 'Brandlabel square endfold', '85.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Cartoon', '75-10-900', 'PFL', 'Brandlabel', '140.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Cartoon', '76-11-1200', 'Hangtag', 'Additional HT', '170.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty & Co', '76-11-2010', 'Hangtag', 'Hangtag (2pcs)', '60.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Cartoon', '76-11-501', 'Hangtag', 'Additional HT', '150.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Betty Barclay', '76-11-6103', 'hangtag', 'HT intensive colour', '35.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('ALL Brands', '76-11-6129', 'Hangtag', 'We respect Cotton incl. CO-ribbon', '70.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('ALL Brands', '76-11-6134', 'Hangtag', 'We respect ECOVERO incl. CO-ribbon', '70.00');
INSERT INTO trims_item (brand, intex_number, label_type, description, price) VALUES ('Cartoon', '76-11-701', 'Hangtag Combo', 'Hangtag', '170.00');

COMMIT;