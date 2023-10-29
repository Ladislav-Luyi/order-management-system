package com.customer.ordermanagementsystem.shop.domain.order.price.discount;

import java.math.BigDecimal;
import java.util.List;

public interface Discountability<T> {
    BigDecimal getDiscountValue(List<T> items);
}
