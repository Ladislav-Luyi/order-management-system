package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.order.Order;
import com.customer.ordermanagementsystem.pojos.order.TerminalReply;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface TerminalService {
    File refreshAndGetFile();
    String composeMessage(Order o);
    void updateOrder(TerminalReply terminalReply);

}
