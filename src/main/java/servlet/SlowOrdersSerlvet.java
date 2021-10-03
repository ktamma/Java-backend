package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection_pool.ConnectionPool;
import dao.OrderDao;
import lombok.SneakyThrows;
import order.Order;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SlowOrdersSerlvet extends HttpServlet {
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String id = request.getParameter("id");
        ServletContext context = getServletContext();
        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");
        OrderDao orderDao = new OrderDao(pool);
        ObjectMapper objectMapper = new ObjectMapper();

        if (id == null){
            List <Order> orders = orderDao.getOrdersSlow();

            response.getWriter().print(objectMapper.writeValueAsString(orders));

        }
        else {
            Order order = orderDao.findOrderByIdSlow(Long.parseLong(id));

            response.getWriter().print(objectMapper.writeValueAsString(order));
        }
    }
}
