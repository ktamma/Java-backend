package servlet;

import config.Config;
import config.HsqlDataSource;

import dao.SpringOrderDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import java.sql.SQLException;




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
            SpringOrderDao dao = ctx.getBean(SpringOrderDao.class);
            context.setAttribute("dao", dao);

        }



    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

