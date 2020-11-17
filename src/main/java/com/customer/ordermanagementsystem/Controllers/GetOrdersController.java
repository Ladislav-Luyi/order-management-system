package com.customer.ordermanagementsystem.Controllers;

import com.customer.ordermanagementsystem.orders.Order;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class GetOrdersController {
    private OrderRepository orderRepository;

    @Autowired
    public GetOrdersController(OrderRepository orderRepository) {
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
                writer.write(o.toString());
                writer.append(newLine);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(new FileSystemResource(file));
    }
}
