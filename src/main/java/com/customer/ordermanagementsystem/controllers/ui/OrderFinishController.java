package com.customer.ordermanagementsystem.controllers.ui;

import com.customer.ordermanagementsystem.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/objednavka")
public class OrderFinishController {

    private final OrderService orderService;

    @Autowired
    public OrderFinishController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/dokoncena")
    public String home(SessionStatus sessionStatus, HttpSession httpsession){
        if (!orderService.isHigherThanMinimalValue())
            return "redirect:/kosik";

        log.info("Sending order to ticketing device: " + orderService.getOrderInstance());

        orderService.saveOrder();

        sessionStatus.setComplete();
        httpsession.invalidate();

        return "ordered";
    }



}



