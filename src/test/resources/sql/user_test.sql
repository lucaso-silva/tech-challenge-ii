INSERT INTO address (street, number, neighborhood, city, zip_code)
VALUES ('Main St', '123', 'Downtown', 'São Paulo', '01000-000');

INSERT INTO user_type (name_type, category)
VALUES ('Owner', 0);

INSERT INTO app_user (name, email, login, password, type_id, address_id, last_modified_date)
VALUES ('Test Owner', 'owner@test.com', 'owner', 'password', 1, 1, CURRENT_DATE);

INSERT INTO restaurant (name, opening_hours, address_id, kitchen_type, owner_id)
VALUES ('Test Restaurant', '10h', 1, 'Brazilian', 1);
