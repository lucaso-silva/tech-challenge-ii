CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(20) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL
);

CREATE TABLE app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(255) NOT NULL,
    address_id BIGINT REFERENCES address(id),
    last_modified_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    opening_hours VARCHAR(100),
    address_id BIGINT NOT NULL REFERENCES address(id),
    kitchen_type VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL REFERENCES app_user(id)
);

CREATE TABLE menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    only_local_consumption BOOLEAN NOT NULL DEFAULT FALSE,
    photo_path VARCHAR(500),
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(id)
);
