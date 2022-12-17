package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.order.TerminalReply;
import org.springframework.stereotype.Service;

@Service
public interface TerminalService {
    void updateOrder(TerminalReply terminalReply);
    String getOrders();

}
