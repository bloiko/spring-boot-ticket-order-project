package com.example.order.service;

import com.example.order.pojos.Order;
import com.example.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Slf4j
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order order(Order order) {
        log.info("Order {} with was taken into handling", order);
        SecurityContextHolder.getContext().getAuthentication();
        return orderRepository.save(order);
    }

    public Iterable<Order> getAllOrders() {
        Iterable<Order> list = orderRepository.findAll();
        return StreamSupport.stream(list.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }

    public boolean deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        return true;
    }

    public Order updateOrder(Order order) {
        Order orderFromDb = orderRepository.getById(order.getId());
        if(orderFromDb != null) {
            return orderRepository.save(orderFromDb);
        }else{
            throw new NoSuchElementException("No such order");
        }
    }
}
