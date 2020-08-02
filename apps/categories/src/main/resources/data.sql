DROP TABLE IF EXISTS categories;

CREATE TABLE categories
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

INSERT INTO categories (name)
VALUES ('Car'),
       ('Bus'),
       ('Plane');