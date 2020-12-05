package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuItem;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
this class should be used by controller for view when someboday is going to save new menu
it should transform raw data in every combination of soup and meal
and save it into database
 */
@Service
@Slf4j
public class MenuItemRawServiceImpl implements MenuItemRawService{
    private final ItemRepository itemRepository;

    @Autowired
    public MenuItemRawServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void convertMenuItemsAndSave(List<MenuItem> menuRawItems) {

        Long counter = 1000l;
        for (MenuItem menuItem : menuRawItems) {
                Type type = null;

            if (menuItem.getSoupOrMeal().equals("polievka")) {
                type = Type.MENU_POLIEVKA;
            }


            else if (menuItem.getSoupOrMeal().equals("jedlo")) {
                type = Type.MENU_JEDLO;
            }

            else{
                continue;
            }

            String name = menuItem.getName();
            BigDecimal price = menuItem.getPrice();
            String date = menuItem.getDate();



            Item item = new Item(++ counter, name, type, price, date);

            log.info("Processing menu item " + item);

            itemRepository.save(item);
        }

    }
}
