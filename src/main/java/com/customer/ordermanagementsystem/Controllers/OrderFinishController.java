package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.Item;
import com.customer.ordermanagementsystem.pojos.Order;
import com.customer.ordermanagementsystem.pojos.OrderInfo;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@SessionAttributes({"order","orderInfo"})
@RequestMapping("/order")
public class OrderFinishController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderFinishController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orderFinished")
    public String home(Order order, OrderInfo orderInfo, SessionStatus sessionStatus, HttpSession httpsession){
        log.info("Sending order to ticketing device: " + order);

        order.setOrderText( order.toString() );

        orderRepository.save(order);

        sessionStatus.setComplete();
        httpsession.invalidate();

        return "ordered";
    }



}



