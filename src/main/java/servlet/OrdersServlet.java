package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection_pool.ConnectionPool;
import dao.OrderDao;
import order.Order;
import util.Util;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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



        Order order = new ObjectMapper().readValue(json, Order.class);
        order.setId(orderDao.insertOrder(order).getId());



        response.setContentType("application/json");

        response.getWriter().print(new ObjectMapper().writeValueAsString(order));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();

        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");

        OrderDao orderDao = new OrderDao(pool);

        String id = req.getParameter("id");

        orderDao.deleteById(Long.parseLong(id));

    }
}
