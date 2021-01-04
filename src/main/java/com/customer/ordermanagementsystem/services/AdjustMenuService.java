package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuEditDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdjustMenuService {
    void transformToItemAndSave(MenuEditDTO menuEditDTO);
    List<Item> getMenuEditDTOAccordingDate(String date);
    void loadMenuEditDTO(MenuEditDTO menuEditDTO, List<Item> items);
    void setTargetDate(String date);
}
