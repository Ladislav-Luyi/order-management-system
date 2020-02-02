package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/order")
public class OrderInfoController {
    @GetMapping("/current")
    public String orderForm(Model model) {
        log.info("Before attribute orderInfo " + model.toString());
        model.addAttribute("orderInfo", new OrderInfo());
        log.info("After attribute orderInfo " + model.toString());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(OrderInfo orderInfo) {
        log.info("Order submitted: " + orderInfo);
        return "redirect:/";
    }
}