package com.customer.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class OrderPlaceHolder {

    private final List<Long> orderList;

    public List<Long> getOrderList() {
        return orderList;
    }
}
