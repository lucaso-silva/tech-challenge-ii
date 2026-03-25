INSERT INTO address (street, number, neighborhood, city, zip_code)
VALUES ('Main St', '123', 'Downtown', 'São Paulo', '01000-000');

INSERT INTO app_user (name, email, user_type)
VALUES ('Test Owner', 'owner@test.com', 'OWNER');

INSERT INTO app_user (name, email, user_type)
VALUES ('Other Owner', 'other@test.com', 'OWNER');

INSERT INTO restaurant
(name, opening_hours, address_id, kitchen_type, owner_id)
VALUES('Test Restaurant', '10h', 1, 'Brazilian', 1);

INSERT INTO 
    menu_item (name, description, price, only_local_consumption, photo_path, restaurant_id)
VALUES 
    ('Feijoada', 'Traditional Brazilian black bean stew with pork', 45.90, false, '/images/feijoada.jpg', 1),
    ('Caipirinha', 'Classic Brazilian cocktail', 15.00, false, '/images/caipirinha.jpg', 1);
