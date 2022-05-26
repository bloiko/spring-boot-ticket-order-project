package com.example.order;

import com.example.order.pojos.Order;
import com.example.order.pojos.User;
import com.example.order.service.OrderService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static com.example.TestHelper.buildOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerMvcIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {

        addTestOrder(1);

        mvc.perform(get("/order/" + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) ;
    }

    private void addTestOrder(long id) {
        Date date = new Date(2022, 20, 20);
        User user = User.builder().id(0L).username("testUsername1").password("password").build();
        Order order = buildOrder(date, id, "source", "destination", user);
        when(orderService.getOrderById(id)).thenReturn(order);
    }
}
