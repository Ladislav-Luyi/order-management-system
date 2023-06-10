package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.item.Type;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void addAllItemsToModel(Model model) {
        Map<Type,List<Item>> map= itemRepository.findAll().stream()
                .collect(Collectors.groupingBy(Item::getType));
        map.entrySet().stream()
                .filter(isValueArrayNotEmpty())
                .filter(isKeyDefinedAsType())
                .forEach(typeListEntry -> addToModel(model, typeListEntry.getKey(), typeListEntry.getValue()));
    }

    private static Predicate<Map.Entry<Type, List<Item>>> isKeyDefinedAsType() {
        return typeListEntry -> Type.isTypeDefined(typeListEntry.getKey().toString());
    }

    private Predicate<Map.Entry<Type, List<Item>>> isValueArrayNotEmpty() {
        return typeListEntry -> typeListEntry.getValue().size() != 0;
    }


    @Override
    public void addSingleItemToModel(Model model, Type type) {
        List<Item> items = itemRepository.findAll().stream()
                .filter( e -> e.getType().equals(type))
                .collect(Collectors.toList());
        addToModel(model, type, items);
    }

    private void addToModel(Model model, Type type, List<Item> items) {
        model.addAttribute(type.toString(), items);
    }
}
