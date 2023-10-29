package com.customer.ordermanagementsystem.shop.service.discount;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.domain.item.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class PizzaDiscount implements Discount<Item> {
    @Override
    public BigDecimal getDiscountValue(List<Item> items) {
        return processDiscountsForPizzas(items);
    }

    private BigDecimal processDiscountsForPizzas(List<Item> items) {

        List<Item> pizzaItems = new ArrayList<>();

        for (Item i : items) {
            if (i.getType() == Type.PIZZA_BIG || i.getType() == Type.PIZZA_NORMAL)
                pizzaItems.add(i);
        }

        log.debug("Items considered for pizza discount: " + pizzaItems);

        List<Item> listItemsWithDiscount = new ArrayList<>();
        List<Item> listItemsWithOutDiscount = new ArrayList<>();

        if (isDiscountForPizzas(pizzaItems)) {
            int discountForXItems = getDiscountCount(pizzaItems);
            pizzaItems.sort(Comparator.comparing(Item::getPrice));

            int counter = 0;
            for (Item item : pizzaItems) {

                if (counter < discountForXItems) {
                    listItemsWithDiscount.add(item);
                } else {
                    listItemsWithOutDiscount.add(item);
                }

                counter++;
            }

        }

        log.debug("Pizza items with discount: " + listItemsWithDiscount);
        log.debug("Pizza items without discount: " + listItemsWithOutDiscount);
        return getTotalDiscountForPizzas(listItemsWithDiscount);
    }

    private BigDecimal getTotalDiscountForPizzas(List<Item> listItemsWithDiscount) {
        BigDecimal discount = new BigDecimal(0);
        for (Item i : listItemsWithDiscount) {
            discount = discount.add(i.getPrice());
        }
        return discount;
    }

    private int getDiscountCount(List<Item> items) {
        return items.size() / 4;
    }

    private boolean isDiscountForPizzas(List<Item> pizzaItems) {
        return (pizzaItems.size() / 4) > 0;
    }
}
