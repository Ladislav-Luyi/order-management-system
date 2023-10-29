package com.customer.ordermanagementsystem.shop.service;

import com.customer.ordermanagementsystem.shop.domain.item.Item;

import java.math.BigDecimal;
import java.util.List;


public interface DiscountService {
    BigDecimal getDiscountValue(List<Item> items);
}
