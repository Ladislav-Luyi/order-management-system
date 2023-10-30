package com.customer.ordermanagementsystem.terminal.controller;

import com.customer.ordermanagementsystem.TestOrderManagementSystemApplication;
import com.customer.ordermanagementsystem.shop.domain.order.CustomerInfo;
import com.customer.ordermanagementsystem.shop.domain.order.Order;
import com.customer.ordermanagementsystem.shop.domain.order.price.PriceDetails;
import com.customer.ordermanagementsystem.shop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestOrderManagementSystemApplication.class})
class TerminalControllersAccTest {
    private final String host = "http://localhost:";

    @Autowired
    OrderRepository orderRepository;

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void loadData() {
        orderRepository.deleteAll();
        PriceDetails priceDetails = PriceDetails.builder()
                .totalPrice(new BigDecimal("100"))
                .totalDiscount(new BigDecimal("0"))
                .priceAfterDiscount(new BigDecimal("100"))
                .build();
        CustomerInfo info = CustomerInfo.builder()
                .name("test user")
                .agreement("yes")
                .payWithCardToDeliveryGuy("yes")
                .street("test street")
                .comment("")
                .telephoneNumber("123456789")
                .build();
        orderRepository.save(Order.builder()
                .priceDetails(priceDetails)
                .customerInfo(info)
                .orderText("test")
                .isPaid(false)
                .build());
    }


    @Test
    void whenGetOrders_thenStatusOKAndMatchBody() {
        ResponseEntity<String> response =
                restTemplate
                        .exchange(host + port + "/orders.txt?u=Terminal&p=password", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).matches("#[a-z0-9]+\\*test\\*123456789#\r");
    }

    @Test
    void whenGetOrdersWrongParam_thenStatusNotAuthorized1() {
        ResponseEntity<String> response =
                restTemplate
                        .exchange(host + port + "/orders.txt?u=Terminal&p=password1", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void whenGetOrdersWrongParam_thenStatusNotAuthorized2() {
        ResponseEntity<String> response =
                restTemplate
                        .exchange(host + port + "/orders.txt", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    void whenGetReplyOrders_thenAcceptOrder() {
        Order orderInput = orderRepository.findOne(Example.of(Order.builder().orderText("test").build())).orElseThrow(RuntimeException::new);
        ResponseEntity<String> response =
                restTemplate
                        .exchange(host + port + "/reply-orders.txt?a=AC001&o=" + orderInput.getId() + "&ak=Accepted&m=OK&dt=04:31&u=Terminal&p=password", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Order orderOutput = orderRepository.findOne(Example.of(Order.builder().orderText("test").build())).orElseThrow(RuntimeException::new);
        assertThat(orderOutput.getTerminalReplyInfo().getStateOfOrder()).isEqualTo("Accepted");
    }
}