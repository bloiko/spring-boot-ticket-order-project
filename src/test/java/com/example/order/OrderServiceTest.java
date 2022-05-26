package com.example.order;

import com.example.order.pojos.User;
import com.example.order.service.OrderService;
import com.example.order.pojos.Order;
import com.example.order.repository.OrderRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Iterator;

import static com.example.TestHelper.buildOrder;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testSave() {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, 1L, "source", "destination", user);
        given(orderRepository.save(order)).willReturn(order);

        Order sendedOrder = orderService.order(order);

        then(sendedOrder)
                .as("Check if sended order is the same as initial.")
                .isEqualTo(order);
        Mockito.verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetAllOrders() {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, 1L, "source", "destination", user);
        Order order2 = buildOrder(date, 2L, "source2", "destination2", user);

        given(orderRepository.findAll()).willReturn(Lists.newArrayList(order, order2));

        Iterable<Order> orders = orderService.getAllOrders();

        then(orders)
                .hasSize(2);
        Iterator<Order> orderIterator = orders.iterator();
        then(orderIterator.next())
                .isEqualTo(order);
        then(orderIterator.next())
                .isEqualTo(order2);
        Mockito.verify(orderRepository, times(1)).findAll();
    }
}
