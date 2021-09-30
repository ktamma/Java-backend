drop table if exists "order";
--
-- drop table if exists item;
--
-- drop sequence if exists orderId;
-- drop sequence if exists itemId;
--
--
CREATE SEQUENCE orderId START WITH 1;
-- CREATE SEQUENCE itemId START WITH 1;
--
--
CREATE TABLE "order"
(
    id          BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('orderId'),
    orderNumber VARCHAR(255) NOT NULL
);
--
-- -- insert into "order" values (1, 'ABC');
--
-- CREATE TABLE item
-- (
--     id       BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('itemId'),
--     name     varchar(255) NOT NULL,
--     quantity bigint       NOT NULL,
--     price    bigint       not null
--
-- );