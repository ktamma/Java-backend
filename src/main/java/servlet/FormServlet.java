package servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDao;
import order.Order;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");


        OrderDao dao = (OrderDao) context.getAttribute("dao");


        try (ctx) {

            Order order = new Order();
            order.setOrderNumber(request.getParameter("orderNumber"));
            order.setId(dao.insertOrder(order).getId());
            response.setContentType("text/plain");

            response.getWriter().print(order.getId());
        }
    }

}