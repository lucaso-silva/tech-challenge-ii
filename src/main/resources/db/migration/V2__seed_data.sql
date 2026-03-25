-- V2: Seed data for Users, Addresses, Restaurants, and Menu Items

-- Addresses
INSERT INTO address (id, street, number, neighborhood, city, zip_code)
VALUES 
    (1001, 'Av. Paulista', '1000', 'Bela Vista', 'São Paulo', '01310-100'),
    (1002, 'Av. Copacabana', '500', 'Copacabana', 'Rio de Janeiro', '22010-000'),
    (1003, 'Av. Afonso Pena', '1500', 'Centro', 'Belo Horizonte', '30130-000');

-- Users (Owners)
INSERT INTO app_user (id, name, email, user_type)
VALUES 
    (2001, 'Carlos Silva', 'carlos@restaurant.com', 'OWNER'),
    (2002, 'Maria Oliveira', 'maria@pizzaria.com', 'OWNER'),
    (2003, 'Kenji Tanaka', 'kenji@sushi.com', 'OWNER');

-- Users (Clients)
INSERT INTO app_user (id, name, email, user_type)
VALUES 
    (2004, 'João Santos', 'joao@email.com', 'CLIENT'),
    (2005, 'Ana Pereira', 'ana@email.com', 'CLIENT'),
    (2006, 'Pedro Costa', 'pedro@email.com', 'CLIENT');

-- Restaurants
INSERT INTO restaurant (id, name, opening_hours, address_id, kitchen_type, owner_id)
VALUES 
    (3001, 'Sabor Brasileiro', '11:00 - 23:00', 1001, 'BRAZILIAN', 2001),
    (3002, 'Pizza Napolitana', '12:00 - 22:00', 1002, 'ITALIAN', 2002),
    (3003, 'Sushi Masters', '11:30 - 23:30', 1003, 'JAPANESE', 2003);

-- Menu Items - Restaurant 1 (Sabor Brasileiro)
INSERT INTO menu_item (id, name, description, price, only_local_consumption, photo_path, restaurant_id)
VALUES 
    (4001, 'Feijoada Completa', 'Traditional black bean stew with pork, rice, and farofa', 45.90, false, '/images/feijoada.jpg', 3001),
    (4002, 'Picanha na Brasa', 'Grilled picanha with potatoes and farofa', 62.00, false, '/images/picanha.jpg', 3001),
    (4003, 'Moqueca de Peixe', 'Bahian fish stew with coconut milk', 55.00, true, '/images/moqueca.jpg', 3001),
    (4004, 'Caipirinha', 'Classic Brazilian cachaça cocktail', 15.00, false, '/images/caipirinha.jpg', 3001);

-- Menu Items - Restaurant 2 (Pizza Napolitana)
INSERT INTO menu_item (id, name, description, price, only_local_consumption, photo_path, restaurant_id)
VALUES 
    (4005, 'Margherita', 'Tomato sauce, mozzarella, basil, olive oil', 38.00, false, '/images/margherita.jpg', 3002),
    (4006, 'Pepperoni', 'Tomato sauce, mozzarella, pepperoni', 42.00, false, '/images/pepperoni.jpg', 3002),
    (4007, 'Quattro Formaggi', 'Four cheese pizza with gorgonzola, parmesan, mozzarella, fontina', 52.00, false, '/images/quattro-formaggi.jpg', 3002),
    (4008, 'Tiramisu', 'Italian coffee-flavored dessert', 18.00, true, '/images/tiramisu.jpg', 3002);

-- Menu Items - Restaurant 3 (Sushi Masters)
INSERT INTO menu_item (id, name, description, price, only_local_consumption, photo_path, restaurant_id)
VALUES 
    (4009, 'Sashimi Salmão', 'Fresh salmon sashimi (8 pieces)', 45.00, false, '/images/sashimi-salmon.jpg', 3003),
    (4010, 'Hot Roll', 'Fried salmon roll with cream cheese (8 pieces)', 38.00, false, '/images/hot-roll.jpg', 3003),
    (4011, 'Temaki Atum', 'Tuna hand roll', 28.00, false, '/images/temaki-tuna.jpg', 3003),
    (4012, 'Ramén', 'Japanese noodle soup with chashu pork', 42.00, true, '/images/ramen.jpg', 3003);