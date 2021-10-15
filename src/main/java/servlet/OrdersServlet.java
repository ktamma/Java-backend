package servlet;

import Exeptions.ValidationError;
import Exeptions.ValidationErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import dao.SpringOrderDao;
import order.Order;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.Util;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class OrdersServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*        response.setContentType("application/json");
        String id = request.getParameter("id");
        ServletContext context = getServletContext();
        ObjectMapper objectMapper = new ObjectMapper();
        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");
        OrderDao orderDao = new OrderDao(pool);

        if (id == null){
            List <Order> orders = orderDao.getOrders();

            response.getWriter().print(objectMapper.writeValueAsString(orders));

        }
        else {
            Order order = orderDao.findOrderById(Long.parseLong(id));

            response.getWriter().print(objectMapper.writeValueAsString(order));
        }*/

        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");


        String json = Util.readStream(request.getInputStream());

        String id = request.getParameter("id");


        SpringOrderDao dao = (SpringOrderDao) context.getAttribute("dao");


        try (ctx) {

            response.setContentType("application/json");
            if (id == null) {

                List<Order> orders = dao.getAllOrders();
                response.getWriter().print(new ObjectMapper().writeValueAsString(orders));
            } else {
                Order order = dao.findOrderById(Long.parseLong(id));
                response.getWriter().print(new ObjectMapper().writeValueAsString(order));
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
/*

        ServletContext context = getServletContext();

        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");

        OrderDao orderDao = new OrderDao(pool);

        String json = Util.readStream(request.getInputStream());



        Order order = new ObjectMapper().readValue(json, Order.class);
        order.setId(orderDao.insertOrder(order).getId());

        response.setContentType("application/json");

        response.getWriter().print(new ObjectMapper().writeValueAsString(order));*/

        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");

        String json = Util.readStream(request.getInputStream());


        Order order = new ObjectMapper().readValue(json, Order.class);

        SpringOrderDao dao = (SpringOrderDao) context.getAttribute("dao");


        try (ctx) {
//            SpringOrderDao dao = ctx.getBean(SpringOrderDao.class);
            response.setContentType("application/json");

            ValidationErrors errors = dao.validateOrder(order);
            if (errors.getErrors().isEmpty()){
                order = dao.insertOrder(order);

                response.getWriter().print(new ObjectMapper().writeValueAsString(order));
            }
            else {
                response.setStatus(400);
                response.getWriter().print(new ObjectMapper().writeValueAsString(errors));
            }

        }



    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        ConnectionPool pool = (ConnectionPool) context.getAttribute("ConnectionPool");
//
//        OrderDao orderDao = new OrderDao(pool);
//
//        String id = req.getParameter("id");
//
//        orderDao.deleteById(Long.parseLong(id));
        String id = req.getParameter("id");

        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");


        SpringOrderDao dao = (SpringOrderDao) context.getAttribute("dao");
        try (ctx) {
//            SpringOrderDao dao = ctx.getBean(SpringOrderDao.class);

            dao.deleteOrderById(Long.parseLong(id));

        }

    }


}
