package com.customer.ordermanagementsystem.Controllers;

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
@RequestMapping("/order")
public class OrderFinishController {

    private final OrderService orderService;

    @Autowired
    public OrderFinishController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orderFinished")
    public String home(SessionStatus sessionStatus, HttpSession httpsession){
        log.info("Sending order to ticketing device: " + orderService.getOrderInstance());

        orderService.saveOrder();

        sessionStatus.setComplete();
        httpsession.invalidate();

        return "ordered";
    }



}



