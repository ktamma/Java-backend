package servlet;

import exceptions.ValidationErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDao;
import order.Order;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.Util;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdersServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");


        String id = request.getParameter("id");


        OrderDao dao = (OrderDao) context.getAttribute("dao");


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

        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");

        String json = Util.readStream(request.getInputStream());


        Order order = new ObjectMapper().readValue(json, Order.class);

        OrderDao dao = (OrderDao) context.getAttribute("dao");


        try (ctx) {

            response.setContentType("application/json");

            ValidationErrors errors = dao.validateOrder(order);
            if (errors.getErrors().isEmpty()) {
                order = dao.insertOrder(order);

                response.getWriter().print(new ObjectMapper().writeValueAsString(order));
            } else {
                response.setStatus(400);
                response.getWriter().print(new ObjectMapper().writeValueAsString(errors));
            }

        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){


        String id = req.getParameter("id");

        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");


        OrderDao dao = (OrderDao) context.getAttribute("dao");
        try (ctx) {

            dao.deleteOrderById(Long.parseLong(id));

        }

    }


}
