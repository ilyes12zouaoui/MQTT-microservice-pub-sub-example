DROP TABLE IF EXISTS products;

CREATE TABLE products
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    category_id   INT

);

INSERT INTO products (name,category_id)
VALUES ('BMW-1',1),
       ('BMW-2',1),
       ('Plane-3',3);