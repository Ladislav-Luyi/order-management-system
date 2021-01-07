package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class MenuMaintenanceServiceImpl implements MenuMaintenanceService {

    private final ItemRepository itemRepository;

    @Autowired
    public MenuMaintenanceServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //second, minute, hour, day of month, month, day(s) of week
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOlderThanTodayMenu() {
        Predicate<Item> isMenuMeal = i -> i.getType().equals(Type.MENU_JEDLO);
        Predicate<Item> isMenuSoup = i -> i.getType().equals(Type.MENU_POLIEVKA);
        Predicate<Item> isNotNullDate = i -> i.getDate() != null;

        log.debug("Running menuMaintenanceServiceImpl");

        List<Item> items = itemRepository.findAll();
        items.stream()
                .filter(isMenuMeal.or(isMenuSoup))
                .filter(isNotNullDate)
                .peek(i -> log.debug("Going to remove " + i.getName() + " " + i.getDate()))
                .forEach(itemRepository::delete);
    }
}
