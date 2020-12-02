package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) { this.itemRepository = itemRepository; }

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

            log.info("Processing for " + type.toString());
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

        log.info("Processing " + type.toString());
        model.addAttribute(type.toString(), tmpList);
    }
}