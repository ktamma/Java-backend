drop table if exists "order";

drop sequence if exists orderId;

CREATE SEQUENCE orderId START WITH 1;

CREATE TABLE "order"
(
    id          BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('orderId'),
    orderNumber VARCHAR(255) NOT NULL
);
