package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.order.Order;
import com.customer.ordermanagementsystem.pojos.order.TerminalReply;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TerminalServiceImpl implements TerminalService {

    private final OrderRepository orderRepository;

    @Autowired
    public TerminalServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private String removeEmojiChars(String s){
        String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
        return s.replaceAll(characterFilter,"");
    }

    public String composeMessage(Order o) {
        StringBuilder s = new StringBuilder();
        s.append("#");
        s.append(o.getId());
        s.append("*");
        s.append(removeEmojiChars( o.getOrderText() ));
        s.append("*");
        s.append(o.getCustomerInfo().getTelephoneNumber());
        s.append("#");
        s.append("\r");
        return s.toString();
    }

    @Override
    public String getOrders(){
        return orderRepository.findAll()   .stream()
                                    .filter(o -> o.getTerminalReplyInfo() == null)
                                    .map(this::composeMessage)
                                    .collect(Collectors.joining(""));
    }

    @Override
    public void updateOrder(TerminalReply terminalReply) {
        try {
            String id = terminalReply.getOrderId();
            Optional<Order> order = orderRepository.findById(id);

            order.ifPresent(o -> {
                o.setTerminalReplyInfo(terminalReply);
                orderRepository.save(o);
            });
        } catch (Exception e){
            log.error(e.toString() + " terminalReply is: " + terminalReply.toString());
        }
    }
}
