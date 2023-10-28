package com.customer.ordermanagementsystem.domain.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceDetails {
    private BigDecimal totalPrice = new BigDecimal(0);
    private BigDecimal totalDiscount = new BigDecimal(0);
    private BigDecimal priceAfterDiscount = new BigDecimal(0);
}