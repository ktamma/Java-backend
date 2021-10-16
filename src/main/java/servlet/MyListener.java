package servlet;

import config.Config;
import config.HsqlDataSource;

import dao.OrderDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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


        var ctx = new AnnotationConfigApplicationContext(Config.class, HsqlDataSource.class);
        context.setAttribute("ctx", ctx);
        try (ctx) {
            OrderDao dao = ctx.getBean(OrderDao.class);
            context.setAttribute("dao", dao);

        }



    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

