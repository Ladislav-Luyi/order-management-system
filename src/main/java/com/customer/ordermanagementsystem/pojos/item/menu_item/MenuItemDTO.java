package com.customer.ordermanagementsystem.pojos.item.menu_item;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class MenuItemDTO {

    private MenuItem menuItem;
    private int indexToRemove;
}
