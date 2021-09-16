package servlet;

import order.Order;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {

    private long id = 0;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        id += 1;
/*        Order order = new Order();
        order.setTitle("from context");

        context.setAttribute("id", post);*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Closed....");
    }
}

