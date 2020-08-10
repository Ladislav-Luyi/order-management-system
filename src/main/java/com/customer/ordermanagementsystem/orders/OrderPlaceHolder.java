package com.customer.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class OrderPlaceHolder {

    private Item item;
    private int indexToRemove;

}
