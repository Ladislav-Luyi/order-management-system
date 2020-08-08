package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.orders.Item;
import com.customer.ordermanagementsystem.orders.Type;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ItemServiceForSpringModelImpl implements ItemServiceForSpringModel {


    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceForSpringModelImpl(ItemRepository itemRepository) { this.itemRepository = itemRepository; }

    @Override
    public void addAllItemsToModel(Model model) {
        List<Item> items = itemRepository.findAll();

        Type[] types = Type.values();
        for (Type type : types)
        {
            List<Item> tmpList = new ArrayList<>();
            for(Item tmpItem : items)
            {
                if (tmpItem.getType() == type) {
                    tmpList.add(tmpItem);
                }
            }

            if (tmpList.size() == 0)
                continue;

            log.info("continue for " + type.toString());
            model.addAttribute(type.toString(), tmpList);


        }
    }

    @Override
    public void addSingleItemToModel(Model model, Type type) {

        List<Item> items = itemRepository.findAll();

        List<Item> tmpList = new ArrayList<>();

        for(Item tmpItem : items)
        {
            if (tmpItem.getType() == type) {
                tmpList.add(tmpItem);
            }
        }

        log.info("continue for " + type.toString());
        model.addAttribute(type.toString(), tmpList);
    }
}
