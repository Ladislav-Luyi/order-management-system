package com.customer.ordermanagementsystem.shop.repository;

import com.customer.ordermanagementsystem.shop.domain.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
