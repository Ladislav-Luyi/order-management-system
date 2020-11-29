package com.customer.ordermanagementsystem.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.Embeddable;

@Data
@Component
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TerminalReply {

    private  String orderId;
    private  String stateOfOrder;
    private  String messageOfOrder;
    private  String acceptanceDate;



}
