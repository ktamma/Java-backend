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


        FormServlet formServlet = new FormServlet();

        ServletRegistration regForm = context.addServlet("formServlet", formServlet);

        regForm.addMapping("/orders/form");


        context.setAttribute("id", new OrderId());





    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

