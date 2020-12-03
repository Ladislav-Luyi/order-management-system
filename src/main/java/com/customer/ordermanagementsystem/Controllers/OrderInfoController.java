package com.customer.ordermanagementsystem.Controllers;


import com.customer.ordermanagementsystem.pojos.order.Order;
import com.customer.ordermanagementsystem.pojos.order.OrderInfo;
import com.customer.ordermanagementsystem.services.DiscountService;
import com.customer.ordermanagementsystem.services.ItemService;
import com.customer.ordermanagementsystem.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;


@Slf4j
@Controller
@SessionAttributes({"order","orderInfo"})
@RequestMapping("/order")
public class OrderInfoController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final DiscountService discountService;

    @Autowired
    public OrderInfoController(ItemService itemService, OrderService orderService, DiscountService discountService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.discountService = discountService;
    }

    @GetMapping("/orderInfo")
    public String showOrderInfoForm(Model model, OrderInfo orderInfo){

        model.addAttribute("orderInfo", new OrderInfo());

        orderService.addOrderedItemsToModel(model, "orderedItems");

        orderService.refreshPrice();

        discountService.refreshDiscounts();

        discountService.addDiscountToModel(model, "discount");

        orderService.addTotalPrice(model, "totalPrice");

        return "orderInfo";

    }


    @PostMapping("/orderInfo")
    public String processOrderInfoForm(@Valid OrderInfo orderInfo, BindingResult bindingResult) {


        log.info("Processing order processOrderInfoForm: " + orderService.getOrderInstance());

        log.info("Processing orderInfo in orderInfo: " + orderInfo);

        orderService.getOrderInstance().setOrderInfo(orderInfo);

        return "redirect:/order/orderFinished";

    }
}
