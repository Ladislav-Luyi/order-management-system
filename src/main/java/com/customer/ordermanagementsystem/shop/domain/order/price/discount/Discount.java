package com.customer.ordermanagementsystem.shop.domain.order.price.discount;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
public class Discount {

    Set<Discountability<Item>> discounts = new HashSet<>();

    {
        discounts.add(new PizzaDiscount());
    }

    public BigDecimal getDiscountValue(List<Item> items) {
        return discounts.stream()
                .map(s -> s.getDiscountValue(items))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
