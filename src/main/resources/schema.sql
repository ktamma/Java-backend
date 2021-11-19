drop table if exists items;

drop sequence if exists orderId;
drop sequence if exists itemId;

DROP TABLE IF EXISTS order_rows;
DROP TABLE IF EXISTS orders;
DROP SEQUENCE IF EXISTS seq1;

DROP TABLE IF EXISTS AUTHORITIES;
DROP TABLE IF EXISTS USERS;

CREATE SEQUENCE seq1 AS INTEGER START WITH 1;

CREATE TABLE orders
(
    id           BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('seq1'),
    order_number VARCHAR(255)
);

CREATE TABLE order_rows
(
    item_name VARCHAR(255),
    price     INT,
    quantity  INT,
    orders_id BIGINT,
    FOREIGN KEY (orders_id)
        REFERENCES orders ON DELETE CASCADE
);

CREATE TABLE USERS
(
    username   VARCHAR(255) NOT NULL PRIMARY KEY,
    password   VARCHAR(255) NOT NULL,
    enabled    BOOLEAN      NOT NULL,
    first_name VARCHAR(255) NOT NULL
);

CREATE TABLE AUTHORITIES
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES USERS
        ON DELETE CASCADE
);