package com.customer.ordermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderManagementSystemApplication {

    public static void main(String[] args) {
        // mvn clean compile assembly:single // for building package with dependencies
        //Add the Java Option -Djdk.tls.client.protocols=TLSv1.2
        System.out.println(com.customer.ordermanagementsystem.OrderManagementSystemApplication.class);
        SpringApplication.run(OrderManagementSystemApplication.class, args);
    }
}
