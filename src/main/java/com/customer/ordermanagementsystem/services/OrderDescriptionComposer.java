package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.Order;

public class OrderDescriptionComposer {

    public String composeOrderText(Order order) {
        return "%s%s%s".formatted(orderListToString(order), order.getCustomerInfo(), priceToString(order));

    }
    private String orderListToString(Order order) {
        StringBuilder s = new StringBuilder();

        String newLine = "\\r";

        for (Item item : order.getShoppingCart()) {
            s.append(newLine);
            s.append(item.getName());
            int innerCounter = 0;
            for (Item subItem : item.getItemList()) {
                if (innerCounter == 0)
                    s.append(newLine);
                s.append("+");
                s.append(subItem.getName());
                s.append(newLine);
                ++innerCounter;
            }
        }
        s.append(newLine);
        return s.toString();
    }

    private String priceToString(Order order) {
        String newLine = "\\r";
        return "Celková cena: %s%s".formatted(order.getPriceDetails().getPriceAfterDiscount(), newLine);
    }

}
