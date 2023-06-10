package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.domain.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
}
