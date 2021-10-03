package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import connectionPool.ConnectionPool;
import dao.OrderDao;
import lombok.SneakyThrows;
import order.Order;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlowOrdersSerlvet extends HttpServlet {
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String id = request.getParameter("id");
        ServletContext context = getServletContext();


        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");
        OrderDao orderDao = new OrderDao(pool);
        Order order1 = orderDao.findOrderById(0L);

        if (id == null){
            List<Order> orders = (ArrayList<Order>) context.getAttribute("orders");
            ObjectMapper objectMapper = new ObjectMapper();

            response.getWriter().print(objectMapper.writeValueAsString(orders));

        }
        else {
            Order order = (Order) context.getAttribute(id);
            ObjectMapper objectMapper = new ObjectMapper();

            response.getWriter().print(objectMapper.writeValueAsString(order));
        }

    }
}
