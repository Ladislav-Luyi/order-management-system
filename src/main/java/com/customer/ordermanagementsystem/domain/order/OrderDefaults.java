package com.customer.ordermanagementsystem.domain.order;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class OrderDefaults {
    @Value("${minimalValueForOrder}")
    BigDecimal minimalValueForOrder;

    public OrderDefaults() {
    }
}