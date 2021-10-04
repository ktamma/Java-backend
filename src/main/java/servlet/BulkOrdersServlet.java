package servlet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection_pool.ConnectionPool;
import dao.OrderDao;
import order.Order;
import util.Util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BulkOrdersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        ServletContext context = getServletContext();

        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");

        OrderDao orderDao = new OrderDao(pool);

        String json = Util.readStream(request.getInputStream());

        List<Order> orders = new ObjectMapper()
                .readValue(json, new TypeReference<List<Order>>() {} );


        List<Long> res = orderDao.insertOrdersBulk(orders);

        response.setContentType("application/json");

        response.getWriter().print(res);
    }

}
