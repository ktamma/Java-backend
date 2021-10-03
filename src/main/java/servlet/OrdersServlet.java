package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection_pool.ConnectionPool;
import dao.OrderDao;
import order.Order;
import util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdersServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
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
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {


        ServletContext context = getServletContext();

        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");

        OrderDao orderDao = new OrderDao(pool);

        String json = Util.readStream(request.getInputStream());

        List<Order> orders = (ArrayList<Order>) context.getAttribute("orders");


        Order order = new ObjectMapper().readValue(json, Order.class);
        order.setId(orderDao.insertOrder(order).getId());

        context.setAttribute("" + order.getId(), order);

        orders.add(order);

        context.setAttribute("orders" , orders);


        response.setContentType("application/json");

        response.getWriter().print(new ObjectMapper().writeValueAsString(order));

    }
}
