package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.pojos.Order;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Optional;

@RestController
@Slf4j
public class TerminalControllers {

    private final OrderRepository orderRepository;

    @Autowired
    public TerminalControllers(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders.txt")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getOrders(){

        String randomPrefix = String.valueOf(Math.random());
        String fileName = randomPrefix+"-orders.txt";
        new File(fileName).delete();
        File file = new File(fileName);

        BufferedWriter writer = null;
        final String newLine = System.getProperty("line.separator");

        try {
            writer = new BufferedWriter(new FileWriter(file, true));

            for (Order o : orderRepository.findAll()){
                if (o.isAccepted() == false) {
                    writer.write(o.toString());
                    writer.append(newLine);
                }
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(new FileSystemResource(file));
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

        log.info("Terminal reply: " + a + " " + o  + " " + ak + " " + m + " " + dt + " " + u + " " + p);

        Optional<Order> order = orderRepository.findById( Long.valueOf(o) );

        order.ifPresent(opt -> {
            opt.setAccepted(true);
            orderRepository.save(opt);
        });

        return o;
    }
}
