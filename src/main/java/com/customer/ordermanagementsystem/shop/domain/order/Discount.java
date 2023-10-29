package com.customer.ordermanagementsystem.shop.domain.order;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.domain.order.discount.PizzaDiscount;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
public class Discount {

    Set<com.customer.ordermanagementsystem.shop.domain.order.discount.Discount<Item>> discounts = new HashSet<>();

    {
        discounts.add(new PizzaDiscount());
    }

    public BigDecimal getDiscountValue(List<Item> items) {
        return discounts.stream()
                .map(s -> s.getDiscountValue(items))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
