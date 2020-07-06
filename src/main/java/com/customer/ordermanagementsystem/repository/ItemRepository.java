package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.orders.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Long> {
}
