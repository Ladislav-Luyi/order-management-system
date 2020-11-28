package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.Order;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import com.customer.ordermanagementsystem.services.TerminalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Optional;

@RestController
@Slf4j
public class TerminalControllers {

    private final OrderRepository orderRepository;
    private final TerminalService terminalService;

    private String user;
    private String password;

    @Autowired
    public TerminalControllers(OrderRepository orderRepository, TerminalService terminalService) {
        this.orderRepository = orderRepository;
        this.terminalService = terminalService;
    }

    @GetMapping("/orders.txt")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getOrders( @RequestParam String u, @RequestParam String p){

        File f = terminalService.refreshAndGetFile();




        return ResponseEntity.ok().body(new FileSystemResource(f));
    }


    @GetMapping("/reply-orders.txt")
    @ResponseBody
    public String acceptOrders(@RequestParam String a,
                             @RequestParam String o,
                             @RequestParam String ak,
                             @RequestParam String m,
                             @RequestParam String dt,
                             @RequestParam String u,
                             @RequestParam String p){
        //Terminal reply: AC001 142 Accepted OK 04:31 admin admin
        log.info("Terminal reply: " + a + " " + o  + " " + ak + " " + m + " " + dt + " " + u + " " + p);

        Optional<Order> order = orderRepository.findById( Long.valueOf(o) );

        order.ifPresent(opt -> {
            opt.setAccepted(true);
            orderRepository.save(opt);
        });

        return o;
    }
}
