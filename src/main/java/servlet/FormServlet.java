package servlet;

import dao.OrderDao;
import order.Order;
import util.ConfigUtil;

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

        OrderDao orderDao = new OrderDao(ConfigUtil.readConnectionInfo());

        ServletContext context = getServletContext();


        Order order = new Order();
        order.setOrderNumber(request.getParameter("orderNumber"));
        order.setId(orderDao.insertOrder(order).getId());
        context.setAttribute("" + order.getId(), order);


        response.setContentType("text/plain");

        response.getWriter().print(order.getId());
    }
}