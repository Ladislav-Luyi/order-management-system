package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuItem;
import com.customer.ordermanagementsystem.repository.MenuItemRepository;
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
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemRawServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public void convertMenuItemsAndSave(List<MenuItem> menuRawItems) {
        menuItemRepository.deleteAll();

        List<MenuItem> menuSoupList = new ArrayList<>();
        List<MenuItem> menuMealList = new ArrayList<>();

        for(MenuItem menuItem : menuRawItems){
            if (menuItem.getSoupOrMeal().equals("polievka")){
                menuSoupList.add(menuItem);
            }

            if (menuItem.getSoupOrMeal().equals("jedlo")){
                menuMealList.add(menuItem);
            }
        }

        for(MenuItem menuSoup : menuSoupList){

            for(MenuItem menuMeal : menuMealList ){
                String name = menuSoup.getName() + " + " + menuMeal.getName();
                BigDecimal price = menuMeal.getPrice();
                String date = menuMeal.getDate();

                MenuItem m = new MenuItem(name, price, date);

                log.info("Processing menu item " + m);
                menuItemRepository.save(m);
            }

        }

    }
}
