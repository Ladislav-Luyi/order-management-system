package com.customer.ordermanagementsystem.domain.order;

import com.customer.ordermanagementsystem.domain.item.Item;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Component
@Document("Orders")
public class Order {

    @Id
    private String id;

    public Order() {
        Date date = new Date();
        long timeMilli = date.getTime();
        id = Long.toString(timeMilli);
        placedAt = new Date();
        priceDetails = new PriceDetails();
    }

    private final PriceDetails priceDetails;
    private Date placedAt;
    private boolean isPaid = false;
    private List<Item> shoppingCart = new ArrayList<>();

    private String orderText = "";

    private CustomerInfo customerInfo;
    private TerminalReply terminalReplyInfo;

    /*
        expected format:
                      #OrderId*Order info#

        UOrderId*
     */

    public BigDecimal getTotalPriceDiscount() {
        return priceDetails.getTotalPrice().subtract(priceDetails.getTotalDiscount());
    }


}
