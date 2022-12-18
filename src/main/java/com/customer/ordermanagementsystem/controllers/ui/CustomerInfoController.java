package com.customer.ordermanagementsystem.controllers.ui;


import com.customer.ordermanagementsystem.pojos.order.CustomerInfo;
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
@SessionAttributes({"order","customerInfo"})
@RequestMapping("/objednavka")
public class CustomerInfoController {

    private final OrderService orderService;
    private final DiscountService discountService;

    @Autowired
    public CustomerInfoController(OrderService orderService, DiscountService discountService) {
        this.orderService = orderService;
        this.discountService = discountService;
    }

    @GetMapping("/formular")
    public String showCustomerInfoForm(Model model){
        if (!orderService.isHigherThanMinimalValue())
            return "redirect:/kosik";

        model.addAttribute("customerInfo",  new CustomerInfo());

        addOrderToModel(model);

        return "customerInfo";
    }

    @PostMapping("/formular")
    public String processCustomerInfoForm(Model model, @Valid CustomerInfo customerInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("BINDING RESULT ERROR");
            addOrderToModel(model);
            return "customerInfo";
        }

        log.info("Processing order processCustomerInfoForm: " + orderService.getOrderInstance());

        log.info("Processing customerInfo in customerInfo: " + customerInfo);

        orderService.setCustomerInfo(customerInfo);

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
