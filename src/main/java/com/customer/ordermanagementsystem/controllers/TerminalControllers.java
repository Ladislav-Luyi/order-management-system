package com.customer.ordermanagementsystem.controllers;

import com.customer.ordermanagementsystem.domain.order.TerminalReply;
import com.customer.ordermanagementsystem.services.TerminalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TerminalControllers {
    private final TerminalService terminalService;

    @Value("${terminalUser}")
    private String user;

    @Value("${terminalPassword}")
    private String password;

    public TerminalControllers(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @GetMapping("/orders.txt")
    @ResponseBody
    public ResponseEntity<String> getOrders(@RequestParam String u, @RequestParam String p) {

        if (!u.equals(user) || !p.equals(password))
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

        return ResponseEntity.ok().body(terminalService.getOrders());
    }


    @GetMapping("/reply-orders.txt")
    @ResponseBody
    public String acceptOrders(@RequestParam String a,
                               @RequestParam String o,
                               @RequestParam String ak,
                               @RequestParam String m,
                               @RequestParam String dt,
                               @RequestParam String u,
                               @RequestParam String p) {
        //AC001 142 Accepted OK 04:31 admin admin
        //a     o   ak       m  dt
        log.info("Terminal reply: " + a + " " + o + " " + ak + " " + m + " " + dt + " " + u + " " + p);

        TerminalReply terminalReply = new TerminalReply(o, ak, m, dt);

        terminalService.updateOrder(terminalReply);

        return o;
    }


}
