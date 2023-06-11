package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;

import java.math.BigDecimal;
import java.util.List;


public interface DiscountService {
    BigDecimal getDiscountValue(List<Item> items);
}
