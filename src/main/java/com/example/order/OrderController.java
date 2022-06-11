package com.example.order;

import com.example.order.service.OrderService;
import com.example.order.pojos.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void orderBook(@RequestBody Order order) {
        orderService.order(order);
    }

    @PutMapping("/order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order) != null;
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/order/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteOrder(@PathVariable Long orderId) {
        return orderService.deleteOrder(orderId);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<Order> getOrders() {
        return orderService.getAllOrders();
    }
}
