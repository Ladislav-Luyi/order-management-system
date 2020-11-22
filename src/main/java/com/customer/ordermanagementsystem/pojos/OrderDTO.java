package com.customer.ordermanagementsystem.pojos;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class OrderDTO {

    private Item item;
    private int indexToRemove;

}
