package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.orders.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
