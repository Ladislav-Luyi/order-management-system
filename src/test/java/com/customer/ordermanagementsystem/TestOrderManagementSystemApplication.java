package com.customer.ordermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestOrderManagementSystemApplication {

	@Bean
	@ServiceConnection
	MongoDBContainer mysqlContainer() {
		return new MongoDBContainer(DockerImageName.parse("mongo:latest"));
	}

	public static void main(String[] args) {
		SpringApplication.from(OrderManagementSystemApplication::main).with(TestOrderManagementSystemApplication.class).run(args);
	}

}
