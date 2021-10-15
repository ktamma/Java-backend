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


//        ServletContext context = getServletContext();
//
//        ConnectionPool pool =(ConnectionPool) context.getAttribute("ConnectionPool");
//
//        OrderDao orderDao = new OrderDao(pool);
//
//
//        Order order = new Order();
//        order.setOrderNumber(request.getParameter("orderNumber"));
//        order.setId(orderDao.insertOrder(order).getId());
//
//        response.setContentType("text/plain");
//
//        response.getWriter().print(order.getId());
    }
}