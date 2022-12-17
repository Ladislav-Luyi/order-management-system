package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface DiscountService {
    void addDiscountToModel(Model model, String nameOfAttributeForMapping);
    void refreshDiscounts();
}
