package com.customer.ordermanagementsystem.terminal.services;

import com.customer.ordermanagementsystem.shop.domain.order.TerminalReply;


public interface TerminalService {
    void updateOrder(TerminalReply terminalReply);

    String getOrders();

}
