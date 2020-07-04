package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.Order;
import com.customer.ordermanagementsystem.orders.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@SessionAttributes({"order","orderInfo"})
@RequestMapping("/order")
public class OrderFinishController {

    @GetMapping("/orderFinished")
    public String home(Order order, OrderInfo orderInfo){
        log.info("Sending order to ticketing device: " + order);
        log.info("Sending order to ticketing device: " + orderInfo);
        return "ordered";
    }



}



