package com.customer.ordermanagementsystem.controllers.ui;

import com.customer.ordermanagementsystem.services.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;


@Slf4j
@Controller
@RequestMapping("/objednavka")
public class OrderFinishController {

    private final OrderService orderService;

    public OrderFinishController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/dokoncena")
    public String home(SessionStatus sessionStatus, HttpSession httpsession) {
        if (!orderService.isHigherThanMinimalValue())
            return "redirect:/kosik";

        orderService.saveOrder();

        sessionStatus.setComplete();
        httpsession.invalidate();

        return "ordered";
    }


}



