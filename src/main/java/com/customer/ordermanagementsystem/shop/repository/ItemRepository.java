package com.customer.ordermanagementsystem.shop.repository;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}
