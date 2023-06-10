package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.domain.item.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findAll();
}
