package com.customer.ordermanagementsystem.services.discounts;

import java.math.BigDecimal;
import java.util.List;

public interface Discount<T> {
    public BigDecimal getDiscountValue(List<T> items);
}
