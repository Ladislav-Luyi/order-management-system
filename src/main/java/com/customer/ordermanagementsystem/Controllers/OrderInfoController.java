package com.customer.ordermanagementsystem.Controllers;


import com.customer.ordermanagementsystem.orders.Order;
import com.customer.ordermanagementsystem.orders.OrderInfo;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/orderInfo")
    public String showOrderInfoForm(Model model, OrderInfo orderInfo){

        model.addAttribute("orderInfo", new OrderInfo());

        return "orderInfo";

    }


    @PostMapping("/orderInfo")
    public String processOrderInfoForm(Order order, @Valid OrderInfo orderInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "order";
        }

        log.info("Processing order in orderInfo: " + order);
        log.info("Processing orderInfo in orderInfo: " + orderInfo);

        order.setOrderInfo(orderInfo);

        return "redirect:/order/orderFinished";

    }
}
