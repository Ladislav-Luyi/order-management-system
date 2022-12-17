package com.customer.ordermanagementsystem.pojos.order;

import com.customer.ordermanagementsystem.pojos.item.Item;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class OrderDTO {
    private Item item;
    private int indexToRemove;
}
