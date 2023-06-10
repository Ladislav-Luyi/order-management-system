package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


public interface ModelService {
    default  <T> void addToModel(Model model, String s, List<T> items) {
        model.addAttribute(s, items);
    }

    default <T> void addToModel(Model model, String s, T element) {
        model.addAttribute(s, element);
    }
}
