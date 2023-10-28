package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.domain.item.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}
