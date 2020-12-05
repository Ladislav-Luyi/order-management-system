package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuItemRawInput;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import com.customer.ordermanagementsystem.repository.MenuItemRepository;
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
    private final MenuItemRawService menuItemRawService;
    private final MenuItemRepository menuItemRepository;


    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, MenuItemRawService menuItemRawService, MenuItemRepository menuItemRepository) {
        this.itemRepository = itemRepository;
        this.menuItemRawService = menuItemRawService;
        this.menuItemRepository = menuItemRepository;
    }



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

        //temporal implementation
        //check date before adding; add only items which match date
//        model.addAttribute("menu", menuItems);
        menuItemRawService.convertMenuItemsAndSave(  MenuItemRawInput.menuItems );

        model.addAttribute("menu", menuItemRepository.findAll() );



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
