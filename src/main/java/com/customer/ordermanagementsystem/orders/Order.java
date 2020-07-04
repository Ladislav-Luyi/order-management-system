package com.customer.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Data
@Component
public class Order {
    private  List<Long> orderList;
}
