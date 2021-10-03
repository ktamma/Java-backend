package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection_pool.ConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PoolInfoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {

        ServletContext context = getServletContext();

        ConnectionPool pool = (ConnectionPool) context.getAttribute("ConnectionPool");


        response.setContentType("application/json");

        Object outPut = new Object() {
            public int inPool = pool.getIdle();
            public int inUse = pool.getActive();

        };

        response.getWriter().print(new ObjectMapper().writeValueAsString(outPut));



    }
}
