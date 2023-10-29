package com.customer.ordermanagementsystem.terminal.service;

import com.customer.ordermanagementsystem.shop.domain.order.TerminalReply;


public interface TerminalService {
    void updateOrder(TerminalReply terminalReply);

    String getOrders();

}
