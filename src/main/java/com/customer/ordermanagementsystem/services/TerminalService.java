package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.order.TerminalReply;


public interface TerminalService {
    void updateOrder(TerminalReply terminalReply);

    String getOrders();

}
