package com.customer.ordermanagementsystem.terminal.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@Component
@NoArgsConstructor
public class TerminalReply {
    private String orderId;
    private String stateOfOrder;
    private String messageOfOrder;
    private String acceptanceDate;

    public TerminalReply(String orderId, String stateOfOrder, String messageOfOrder, String acceptanceDate) {
        this.orderId = orderId;
        this.stateOfOrder = stateOfOrder;
        this.messageOfOrder = messageOfOrder;
        this.acceptanceDate = acceptanceDate;
    }
}
