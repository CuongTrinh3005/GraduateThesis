package com.example.onlinephoneshop.controller;

import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RunOutOfProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    private User customer = new User();
    private Product product = new Product();
    private Order order = new Order();

    @Before
    public void setUp(){
        customer = new User();
        customer.setUserId("US1");
        customer.setUsername("cuongtq");

        product = new Product();
        product.setProductId("PD1");
        product.setProductName("PD1");
        product.setUnitPrice(100000F);
        product.setQuantity(0L);

        order = new Order();
        order.setUser(customer);
        order.setPayment(new Payment(1,"CASH"));

        OrderDetails.OrderDetailID detailID = new OrderDetails.OrderDetailID(order.getOrderId(), product.getProductId());
        OrderDetails details = new OrderDetails(detailID, 1, product.getDiscount()
                , product.getUnitPrice(), order, product);
        List<OrderDetails> detailsList = new ArrayList<>();
        detailsList.add(details);
        order.setOrderDetails(detailsList);
    }

    @Test
    @WithMockUser( roles = "USER")
    public void whenRunOutOfProduct_ThenThrowCustomException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/")
                .content(asJsonString(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}