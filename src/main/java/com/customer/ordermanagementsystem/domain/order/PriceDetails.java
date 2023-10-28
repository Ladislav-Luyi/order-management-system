package com.customer.ordermanagementsystem.domain.order;

import java.math.BigDecimal;

public class PriceDetails {
    private BigDecimal totalPrice = new BigDecimal(0);
    private BigDecimal totalDiscount = new BigDecimal(0);
    private BigDecimal totalPriceDiscount = new BigDecimal(0);

    public PriceDetails() {
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return this.totalDiscount;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setTotalPriceDiscount(BigDecimal totalPriceDiscount) {
        this.totalPriceDiscount = totalPriceDiscount;
    }
}