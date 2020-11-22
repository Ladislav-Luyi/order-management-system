package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.orders.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {
    List<Item> findAll();
}
