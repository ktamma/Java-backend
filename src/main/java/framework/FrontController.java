package framework;

import com.fasterxml.jackson.databind.ObjectMapper;

import framework.annotations.Delete;
import framework.annotations.Get;
import framework.annotations.Post;
import lombok.SneakyThrows;
import order.Order;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.Util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrontController extends HttpServlet {

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");
        var beans = (Collection<Object>) context.getAttribute("beans");
        Long id = null;
        String mydata = request.getRequestURI();
        Pattern pattern = Pattern.compile("\\/\\d+");
        Matcher matcher = pattern.matcher(mydata);
        if (matcher.find()) {
            id = Long.parseLong(matcher.group(0).substring(1));
        }
        try (ctx) {

            for (Object bean : beans) {
                Method[] methods = bean.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    Get annotation = method.getAnnotation(Get.class);
                    if (annotation != null && Pattern.matches(annotation.value(), request.getRequestURI())) {
                        response.setContentType("application/json");
                        if (id == null) {
                            response.getWriter().print(new ObjectMapper().writeValueAsString(method.invoke(bean)));
                        } else {
                            response.getWriter().print(new ObjectMapper().writeValueAsString(method.invoke(bean, id)));
                        }
                    }
                }
            }
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String json = Util.readStream(request.getInputStream());


        Order order = new ObjectMapper().readValue(json, Order.class);
        ServletContext context = getServletContext();

        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");
        var beans = (Collection<Object>) context.getAttribute("beans");

        try (ctx) {

            for (Object bean : beans) {
                Method[] methods = bean.getClass().getDeclaredMethods();

                for (Method method : methods) {
                    Post annotation = method.getAnnotation(Post.class);

                    if (annotation != null && Objects.equals(annotation.value(), request.getRequestURI())) {
                        if (order.getClass() == method.getParameterTypes()[0]) {

                            response.setContentType("application/json");
                            response.getWriter().print(new ObjectMapper().writeValueAsString(method.invoke(bean, order)));

                        }
                    }

                }
            }
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        ServletContext context = getServletContext();
        var ctx = (AnnotationConfigApplicationContext) context.getAttribute("ctx");
        var beans = (Collection<Object>) context.getAttribute("beans");
        Long id = null;
        String mydata = request.getRequestURI();
        Pattern pattern = Pattern.compile("\\/\\d+");
        Matcher matcher = pattern.matcher(mydata);
        if (matcher.find()) {
            id = Long.parseLong(matcher.group(0).substring(1));
        }
        try (ctx) {
            for (Object bean : beans) {
                Method[] methods = bean.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    Delete annotation = method.getAnnotation(Delete.class);
                    if (annotation != null && Pattern.matches(annotation.value(), request.getRequestURI())) {
                        response.setContentType("application/json");

                        method.invoke(bean, id);

                    }
                }
            }
        }
    }
}

