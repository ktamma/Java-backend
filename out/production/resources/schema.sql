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

insert into orders(orderNumber) VALUES ('ABC');
insert into orders(orderNumber) VALUES ('Bca');
insert into orders(orderNumber) VALUES ('123');
insert into orders(orderNumber) VALUES ('wqewqABC');
insert into orders(orderNumber) VALUES ('ArfqetBC');


insert into items(orderId, itemName, quantity, price) VALUES (1,'cpu', 123, 10);
insert into items(orderId, itemName, quantity, price) VALUES (2,'cpu', 123, 10);
insert into items(orderId, itemName, quantity, price) VALUES (3,'cpu', 123, 10);
insert into items(orderId, itemName, quantity, price) VALUES (1,'Motherboard', 123, 10);
insert into items(orderId, itemName, quantity, price) VALUES (1,'Monitor', 123, 10);
insert into items(orderId, itemName, quantity, price) VALUES (1,'RAM', 123, 10);
SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price, items.orderId
                FROM orders
                LEFT JOIN items ON orders.id = items.orderId order by 1;
/*select orders.id, orders.orderNumber, items.itemName, items.quantity, items.price from orders, items
WHERE orders.id = items.orderId;
SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price
FROM orders
LEFT JOIN items ON orders.id = items.orderId where orders.id = 4;*/

/*select orders.id, orders.orderNumber, items.itemName, items.quantity, items.price from orders, items
WHERE orders.id = items.orderId;
SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price
FROM orders
         LEFT JOIN items ON orders.id = items.orderId*/
-- select orders.id, orders.orderNumber from orders;

