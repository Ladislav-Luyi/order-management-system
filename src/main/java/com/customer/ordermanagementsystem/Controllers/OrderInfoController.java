package com.customer.ordermanagementsystem.Controllers;


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
    public OrderInfoController(OrderService orderService, DiscountService discountService, OrderInfo orderInfo) {
        this.orderService = orderService;
        this.discountService = discountService;
    }

    @GetMapping("/formular")
    public String showOrderInfoForm(Model model){

        model.addAttribute("orderInfo",  new OrderInfo());

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");

        return "orderInfo";
    }


    @PostMapping("/formular")
    public String processOrderInfoForm( @Valid OrderInfo orderInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("BINDING RESULT ERROR");
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
}
