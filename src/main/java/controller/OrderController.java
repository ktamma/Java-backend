package controller;

import dao.OrderDao;

import model.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    private OrderDao dao;

    public OrderController(OrderDao repository) {
        this.dao = repository;
    }

    @PostMapping("orders")
    public Order createOrder(@RequestBody @Valid Order order) {
        return dao.save(order);
    }

    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrders() {
        return dao.getAllOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        return dao.findOrderById(id);
    }

    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        dao.deleteOrderById(id);
    }

}
