package com.customer.ordermanagementsystem.controllers;


import com.customer.ordermanagementsystem.pojos.order.OrderInfo;
import com.customer.ordermanagementsystem.services.DiscountService;
import com.customer.ordermanagementsystem.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@Controller
@SessionAttributes({"order","orderInfo"})
@RequestMapping("/objednavka")
public class OrderInfoController {

    private final OrderService orderService;
    private final DiscountService discountService;

    @Autowired
    public OrderInfoController(OrderService orderService, DiscountService discountService) {
        this.orderService = orderService;
        this.discountService = discountService;
    }

    @GetMapping("/formular")
    public String showOrderInfoForm(Model model){
        if (!orderService.isHigherThanMinimalValue())
            return "redirect:/kosik";

        model.addAttribute("orderInfo",  new OrderInfo());

        addOrderToModel(model);

        return "orderInfo";
    }

    @PostMapping("/formular")
    public String processOrderInfoForm(Model model, @Valid OrderInfo orderInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("BINDING RESULT ERROR");
            addOrderToModel(model);
            return "orderInfo";
        }

        log.info("Processing order processOrderInfoForm: " + orderService.getOrderInstance());

        log.info("Processing orderInfo in orderInfo: " + orderInfo);

        orderService.setOrderInfo(orderInfo);

        return "redirect:/objednavka/dokoncena";
    }

    @GetMapping("/podmienky")
    public String legalConditions() {

        return "conditions";
    }

    private void addOrderToModel(Model model) {
        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");
    }
}
