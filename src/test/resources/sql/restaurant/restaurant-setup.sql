INSERT INTO address (id, street, number, neighborhood, city, zip_code)
VALUES (100, 'any-street-name', 'any-number', 'any-neighborhood', 'any-city', 'any-zip-code');

INSERT INTO app_user (id, name, email, user_type)
VALUES (200, 'Jonh Doe', 'jdoe@email.com', 'OWNER');

INSERT INTO app_user (id, name, email, user_type)
VALUES (201, 'Anna Doe', 'adoe@email.com', 'CLIENT');

INSERT INTO restaurant(id, name, opening_hours, address_id, kitchen_type, owner_id)
VALUES(1, 'any-restaurant-name', 'any-opening-hours', 100, 'brazilian', 200);
