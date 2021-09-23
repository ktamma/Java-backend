
package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import mapper.OrderMapper;
import order.Order;
import util.Util;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdersServlet extends HttpServlet {
    private long id = 0;


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ServletContext context = getServletContext();
        Order order = (Order) context.getAttribute(request.getParameter("id"));
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().print(objectMapper.writeValueAsString(order));
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {


        String json = Util.readStream(request.getInputStream());


        Order order = new ObjectMapper().readValue(json, Order.class);
        order.setId(id++);

        ServletContext context = getServletContext();
        System.out.println("" + order.getId());
        context.setAttribute("" + order.getId(), order);

        response.setContentType("application/json");

        response.getWriter().print(new ObjectMapper().writeValueAsString(order));

//        OrderMapper mapper = new OrderMapper();
//
//        Order order = mapper.parse(json);
//
//        order.setId(id++);
//
//        ServletContext context = getServletContext();
//        System.out.println(""+order.getId());
//        context.setAttribute("" + order.getId(), order);
//
//
//        response.setContentType("application/json");
//        response.getWriter().println(mapper.stringify(order));
    }
}
