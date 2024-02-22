package com.customer.ordermanagementsystem.monitoring.controller;

import com.customer.ordermanagementsystem.TestOrderManagementSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestOrderManagementSystemApplication.class})
class HeartBeatTest {

    private final String host = "http://localhost:";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    void whenHead_thenStatusOK() {
        ResponseEntity<String> response =
                restTemplate
                        .exchange(host + port + "/heartbeat", HttpMethod.HEAD, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}