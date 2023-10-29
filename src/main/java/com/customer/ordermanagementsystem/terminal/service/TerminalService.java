package com.customer.ordermanagementsystem.terminal.service;

import com.customer.ordermanagementsystem.terminal.domain.TerminalReply;


public interface TerminalService {
    void updateOrder(TerminalReply terminalReply);

    String getOrders();

}
