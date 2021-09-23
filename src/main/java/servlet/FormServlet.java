package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import order.Order;
import util.Util;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormServlet extends HttpServlet {

    private long id = 0;
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
        Order order = new Order();
        order.setId(id++);
        order.setOrderNumber(request.getParameter("orderNumber"));

        ServletContext context = getServletContext();
        System.out.println("" + order.getId());
        context.setAttribute("" + order.getId(), order);



        response.setContentType("text/plain");

        response.getWriter().print(order.getId());
    }
}