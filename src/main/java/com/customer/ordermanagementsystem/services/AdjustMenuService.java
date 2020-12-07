package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuEditDTO;

public interface AdjustMenuService {
    void transformToItemAndSave(MenuEditDTO menuEditDTO);
    MenuEditDTO getMenuEditDTOAccordingDate(String date);
}
