package com.customer.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class OrderPlaceHolder {

    private final List<Item> orderList;

    public List<Item> getOrderList() {
        return orderList;
    }
}
