package com.customer.ordermanagementsystem.domain.order;

import com.customer.ordermanagementsystem.domain.item.Item;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class OrderDTO {
    private Item item;
    private int indexToRemove;
}
