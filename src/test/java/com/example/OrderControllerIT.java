package com.example;

import com.example.order.OrderController;
import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;
import com.example.order.pojos.User;
import com.example.order.repository.OrderRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.TestHelper.buildOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.core.userdetails.User.withUsername;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { DbConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest(classes = SpringBootProjectApplication.class)
public class OrderControllerIT {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderController orderController;

    @Transactional
    @Rollback
    @Test
    public void testRetrieveOrdersSync() throws Exception {
        setGrantedAuthority();
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, 1L, "source1", "destination1", user);

        orderController.orderBook(order);

        List<Order> all = orderRepository.findAll();
        assertTrue(all.stream()
                .anyMatch(orderTemp -> areOrdersEqual(orderTemp, order)));
    }


    @Transactional
    @Rollback
    @Test
    public void testRetrieveOrdersAsync() {
        setGrantedAuthority();
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(10L).username("testUsername1").password("password").build();
        Order order1 = buildOrder(date, 1, "source", "destination", user);
        Order order2 = buildOrder(date, 2, "source2", "destination2", user);
        Order order3 = buildOrder(date, 3, "source3", "destination3", user);
        List<Order> orders = Lists.newArrayList(order1, order2, order3);
        orders.parallelStream()
                .map(order -> orderController.orderBook(order))
                .collect(Collectors.toList());

        List<Order> all = orderRepository.findAll();
        assertEquals(all.size(), 3);
    }

    private void setGrantedAuthority() {
        ArrayList<SimpleGrantedAuthority> role_user = Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = withUsername("testUsername1")
                .authorities(role_user)
                .password("password")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new PreAuthenticatedAuthenticationToken(userDetails, userDetails,
                        role_user));
    }

    private boolean areOrdersEqual(Order orderTemp, Order order) {
        Ticket ticketTemp = orderTemp.getTicket();
        Ticket ticket = order.getTicket();

        return orderTemp.getUser().getUsername().equals(order.getUser().getUsername()) &&
                ticketTemp.getSource().equals(ticket.getSource()) &&
                ticketTemp.getDestination().equals(ticket.getDestination()) &&
                ticketTemp.getPrice().equals(ticket.getPrice());
    }
}
