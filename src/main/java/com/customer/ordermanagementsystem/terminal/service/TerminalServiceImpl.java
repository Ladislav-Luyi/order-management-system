package com.customer.ordermanagementsystem.terminal.service;

import com.customer.ordermanagementsystem.shop.domain.order.Order;
import com.customer.ordermanagementsystem.shop.domain.order.TerminalReply;
import com.customer.ordermanagementsystem.shop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TerminalServiceImpl implements TerminalService {

    private final OrderRepository orderRepository;

    @Autowired
    public TerminalServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private String removeEmojiChars(String s) {
        String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
        return s.replaceAll(characterFilter, "");
    }

    public String composeMessage(Order o) {
        String s = "#" +
                o.getId() +
                "*" +
                removeEmojiChars(o.getOrderText()) +
                "*" +
                o.getCustomerInfo().getTelephoneNumber() +
                "#" +
                "\r";
        return s;
    }

    @Override
    public String getOrders() {
        return orderRepository.findAll().stream()
                .filter(o -> o.getTerminalReplyInfo() == null)
                .map(this::composeMessage)
                .collect(Collectors.joining(""));
    }

    @Override
    public void updateOrder(TerminalReply terminalReply) {
        try {
            String id = terminalReply.getOrderId();
            Optional<Order> order = orderRepository.findById(id);
            if (order.isPresent()) {
                order.get().setTerminalReplyInfo(terminalReply);
                orderRepository.save(order.get());
            } else {
                log.error("Order with id " + id + " was not found");
            }
        } catch (Exception e) {
            log.error(e + " terminalReply is: " + terminalReply.toString());
        }
    }
}
