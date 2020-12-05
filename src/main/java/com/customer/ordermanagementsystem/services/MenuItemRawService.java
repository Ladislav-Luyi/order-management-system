package com.customer.ordermanagementsystem.services;


import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuItemRawService {
    void convertMenuItemsAndSave(List<MenuItem> menuRawItems);
}
