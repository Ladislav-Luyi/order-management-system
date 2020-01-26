package com.alte.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Order {
    private String name;
    private List<Item> itemList;
}
