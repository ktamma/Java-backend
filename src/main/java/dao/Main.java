package dao;

import config.Config;
import config.HsqlDataSource;
import item.Item;
import order.Order;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(Config.class, HsqlDataSource.class);

        try (ctx) {
            OrderDao dao = ctx.getBean(OrderDao.class);
            OrderDao dao2 = ctx.getBean(OrderDao.class);

            Order order = new Order();
            order.setOrderNumber("HELLO");

            Item item = new Item();
            item.setQuantity(123);
            item.setPrice(124);
            item.setItemName("123");

            order.setOrderRows(new ArrayList<>());
            order.addItem(item);
            order.addItem(item);

            dao2.insertOrder(order);
            dao.insertOrder(order);

            System.out.println(dao.getAllOrders());
            System.out.println(dao.findOrderById(1L));


        }
    }
}

