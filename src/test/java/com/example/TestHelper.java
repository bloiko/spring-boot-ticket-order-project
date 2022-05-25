package com.example;

import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;
import com.example.order.pojos.User;

import java.util.Date;

public class TestHelper {
    public static Order buildOrder(Date date, long l, String source, String destination, User user) {
        Ticket ticket = new Ticket(5L, source, destination, date, 1000L);
        return new Order(l, user, ticket);
    }
}
