package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.entity.OrderDetails.OrderDetailID;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.repository.OrderRepository;
import com.example.onlinephoneshop.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
public class RunOutOfProductServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    private User customer = new User();
    private Product product = new Product();
    private Order order = new Order();

    @BeforeEach
    public void init(){
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

        OrderDetailID detailID = new OrderDetailID(order.getOrderId(), product.getProductId());
        OrderDetails details = new OrderDetails(detailID, 1, product.getDiscount()
                , product.getUnitPrice(), order, product);
        List<OrderDetails> detailsList = new ArrayList<>();
        detailsList.add(details);
        order.setOrderDetails(detailsList);
    }

    @Test()
    public void whenRunOutOfProduct_ThenThrowException(){
        // When
        Mockito.when(orderRepository.save(order)).thenThrow(CustomException.class);
        // Then
        assertThatThrownBy(() -> orderService.saveOrder(order))
                .isInstanceOf(CustomException.class);
    }
}