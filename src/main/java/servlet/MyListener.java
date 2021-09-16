package servlet;



import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        OrdersServlet servlet = new OrdersServlet();

        ServletRegistration reg = context.addServlet("orderServlet", servlet);

        reg.addMapping("/api/orders");




    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

