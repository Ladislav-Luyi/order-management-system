package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.orders.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item,Long> {
    List<Item> findAll();
}
