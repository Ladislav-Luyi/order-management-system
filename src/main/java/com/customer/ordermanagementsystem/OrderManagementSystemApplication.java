package com.customer.ordermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderManagementSystemApplication {

    public static void main(String[] args) {
        // mvn clean compile assembly:single // for building package with dependencies
        System.out.println(com.customer.ordermanagementsystem.OrderManagementSystemApplication.class);
        SpringApplication.run(OrderManagementSystemApplication.class, args);



    }

}
