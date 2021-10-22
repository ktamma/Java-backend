package controller

import dao.OrderDao
import order.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class OrderController(private val dao: OrderDao) {
    @PostMapping("orders")
    fun createOrder(@RequestBody @Valid order: Order?): Order {
        return dao.insertOrder(order!!)
    }

    @GetMapping("orders")
    fun getOrders(): List<Order> {
       return dao.allOrders;
    }

    @GetMapping("orders/{id}")
    fun getOrder(@PathVariable id: Long?): Order {
        return dao.findOrderById(id!!)!!
    }

    @DeleteMapping("orders/{id}")
    fun deleteOrder(@PathVariable id: Long?) {
        dao.deleteOrderById(id!!)
    }
}