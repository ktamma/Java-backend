package framework;

import dao.OrderDao;
import framework.annotations.Delete;
import framework.annotations.Get;
import framework.annotations.MyController;
import framework.annotations.Post;
import order.Order;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.util.List;

@MyController
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrderController {

    private final OrderDao repository;

    public OrderController(OrderDao repository) {
        this.repository = repository;
    }

    @Get("/api/v2/orders")
    public List<Order> getAllOrders() {
        return repository.getAllOrders();
    }

    @Get("/api/v2/orders/(\\d+)")
    public Order getOrderById(Long id) {
        return repository.findOrderById(id);
    }

    @Post("/api/v2/orders")
    public Order createOrder(Order order) {
        return repository.insertOrder(order);
    }

    @Delete("/api/v2/orders/(\\d+)")
    public void deleteOrder(Long id) {
        repository.deleteOrderById(id);
    }

}