package com.customer.ordermanagementsystem.shop.domain.order.discount;

import java.math.BigDecimal;
import java.util.List;

public interface Discount<T> {
    BigDecimal getDiscountValue(List<T> items);
}
