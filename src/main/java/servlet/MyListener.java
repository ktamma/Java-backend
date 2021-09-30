package servlet;

import order.Order;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.CreateDatabase.createSchema;


@WebListener
public class MyListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            createSchema();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        ServletContext context = sce.getServletContext();

        OrdersServlet servlet = new OrdersServlet();

        ServletRegistration reg = context.addServlet("orderServlet", servlet);

        reg.addMapping("/api/orders");


        FormServlet formServlet = new FormServlet();

        ServletRegistration regForm = context.addServlet("formServlet", formServlet);

        regForm.addMapping("/orders/form");

        List<Order> orders = new ArrayList<>();
        context.setAttribute("orders", orders);





    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

