package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface DiscountService {
    BigDecimal getDiscountValue(List<Item> items);
}
