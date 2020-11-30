package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.pojos.order.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
}
