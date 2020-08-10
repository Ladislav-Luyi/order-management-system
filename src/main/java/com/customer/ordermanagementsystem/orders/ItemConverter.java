package com.customer.ordermanagementsystem.orders;


import com.customer.ordermanagementsystem.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemConverter implements Converter<String, Item> {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemConverter(ItemRepository itemRepository) {

        this.itemRepository = itemRepository;
    }

    @Override
    public Item convert(String source) {

        List<Item> items = new ArrayList<>();

        itemRepository.findAll().forEach(i -> items.add(i));

        for (Item item : items) {

            if ( item.getId().toString().equals(source) )

                return item;
        }

        return null;
    }

}