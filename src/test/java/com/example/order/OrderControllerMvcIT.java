package com.example.order;

import com.example.order.pojos.Order;
import com.example.order.pojos.User;
import com.example.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static com.example.TestHelper.buildOrder;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerMvcIT {
    private MockMvc mvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void givenOrderController_whenGetOrder_thenStatus200()
            throws Exception {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, 1, "source", "destination", user);
        when(orderService.getOrderById(order.getId())).thenReturn(order);

        mvc.perform(get("/order/" + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(order)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenOrderController_whenGetOrders_thenStatus200()
            throws Exception {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order1 = buildOrder(date, 1, "source", "destination", user);
        Order order2 = buildOrder(date, 2, "source2", "destination2", user);
        List<Order> orderList = asList(order1, order2);
        when(orderService.getAllOrders()).thenReturn(orderList);

        mvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderList)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenOrderController_whenOrder_thenStatus200()
            throws Exception {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, 1, "source", "destination", user);
        when(orderService.order(order)).thenReturn(order);

        mvc.perform(post("/order").content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderController_whenDeleteOrder_thenStatus200()
            throws Exception {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, 1, "source", "destination", user);
        when(orderService.deleteOrder(1L)).thenReturn(true);

        mvc.perform(delete("/order/" + "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderController_whenUpdateOrder_thenStatus200()
            throws Exception {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, 1, "source", "destination", user);
        when(orderService.updateOrder(order)).thenReturn(order);

        mvc.perform(put("/order").content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
