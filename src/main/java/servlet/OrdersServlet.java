
package servlet;

import mapper.OrderMapper;
import order.Order;
import util.Util;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdersServlet extends HttpServlet {
    private long id = 0;


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print("{}");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {


        String json = Util.readStream(request.getInputStream());

        OrderMapper mapper = new OrderMapper();

        Order order = mapper.parse(json);

        order.setId(id++);

        response.setContentType("application/json");
        response.getWriter().println(mapper.stringify(order));
    }
}
