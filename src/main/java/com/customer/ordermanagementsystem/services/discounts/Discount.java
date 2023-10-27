package com.customer.ordermanagementsystem.services.discounts;

import java.math.BigDecimal;
import java.util.List;

public interface Discount<T> {
    BigDecimal getDiscountValue(List<T> items);
}
