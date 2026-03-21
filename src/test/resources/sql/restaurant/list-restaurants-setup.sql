INSERT INTO address (id, street, number, neighborhood, city, zip_code)
VALUES (100, 'any-street-name', 'any-number', 'any-neighborhood', 'any-city', 'any-zip-code');

INSERT INTO app_user (id, name, email, login, password, user_type, address_id)
VALUES (200, 'Jonh Doe', 'jdoe@email.com', 'jdoe', 'senha123', 'owner', 100);

INSERT INTO restaurant(id, name, opening_hours, address_id, kitchen_type, owner_id)
VALUES(1, 'first-restaurant-name', 'any-opening-hours', 100, 'brazilian', 200);

INSERT INTO restaurant(id, name, opening_hours, address_id, kitchen_type, owner_id)
VALUES(2, 'second-restaurant-name', 'another-opening-hours', 100, 'italian', 200);
