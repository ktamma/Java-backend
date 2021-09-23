package servlet;

import order.Order;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        response.getWriter().print("{}");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {


        ServletContext context = getServletContext();

        OrderId orderId = (OrderId) context.getAttribute("id");

        Order order = new Order();
        order.setId(orderId.increase());
        order.setOrderNumber(request.getParameter("orderNumber"));
        context.setAttribute("" + order.getId(), order);

        context.setAttribute("id", orderId);


        response.setContentType("text/plain");

        response.getWriter().print(order.getId());
    }
}