package com.customer.ordermanagementsystem.repository;

import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuItem;
import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem,Long> {
}
