drop table if exists items;

drop table if exists orders;
drop sequence if exists orderId;
drop sequence if exists itemId;

CREATE SEQUENCE orderId START WITH 1;
CREATE SEQUENCE itemId START WITH 1;



CREATE TABLE orders
(
    id          BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('orderId'),
    orderNumber VARCHAR(255) NOT NULL
);

CREATE TABLE items
(
    id       BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('itemId'),
    orderId  BIGINT       NOT NULL,
    itemName VARCHAR(255) NOT NULL,
    quantity BIGINT       NOT NULL,
    price    BIGINT       NOT NULL,
    FOREIGN KEY (orderId) REFERENCES orders(id)
);


