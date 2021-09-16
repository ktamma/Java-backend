//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package servlet;

import mapper.OrderMapper;
import order.Order;
import util.Util;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/api/orders"})
public class OrdersServlet extends HttpServlet {
    private long id = 0;
    public OrdersServlet() {
    }

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
        try {
            Order order1 = mapper.parseObj(json, Order.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        id += 1;
        order.id = id;
        response.setContentType("application/json");
        response.getWriter().println(mapper.stringify(order));
    }
}
