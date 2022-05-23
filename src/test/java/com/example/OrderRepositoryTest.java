package com.example;

import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;
import com.example.order.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.example.TestHelper.buildOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { DbConfiguration.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void givenStudent_whenSave_thenGetOk() {
        Date date = new Date(2022, 20, 20);
        Order order = buildOrder(date, 1L, "source", "destination", "testUsername");

        orderRepository.save(order);

        List<Order> all = orderRepository.findAll();
        Order order2 = all.get(0);
        Ticket ticket2 = order2.getTicket();
        assertEquals("testUsername", order2.getUser().getUsername());
        assertEquals("source", ticket2.getSource());
        assertEquals("destination", ticket2.getDestination());
        assertEquals(date, ticket2.getDate());
        assertEquals(order.getTicket().getPrice(), ticket2.getPrice());
    }
}
