package com.customer.ordermanagementsystem.pojos.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class TerminalReply {

    private  String orderId;
    private  String stateOfOrder;
    private  String messageOfOrder;
    private  String acceptanceDate;



}
