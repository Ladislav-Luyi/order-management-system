package com.customer.ordermanagementsystem.shop.service;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.service.discount.Discount;
import com.customer.ordermanagementsystem.shop.service.discount.PizzaDiscount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DiscountServiceImpl implements DiscountService {
    Set<Discount<Item>> services = new HashSet<>();

    {
        services.add(new PizzaDiscount());
    }

    public BigDecimal getDiscountValue(List<Item> items) {
        return services.stream()
                .map(s -> s.getDiscountValue(items))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
