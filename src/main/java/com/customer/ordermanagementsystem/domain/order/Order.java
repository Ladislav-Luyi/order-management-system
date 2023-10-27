package com.customer.ordermanagementsystem.domain.order;

import com.customer.ordermanagementsystem.domain.item.Item;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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
    }

    private Date placedAt;
    private BigDecimal totalPrice = new BigDecimal(0);
    private BigDecimal totalDiscount = new BigDecimal(0);
    private BigDecimal totalPriceDiscount = new BigDecimal(0);
    private boolean isPaid = false;
    private List<Item> orderList = new ArrayList<>();

    @Value("${minimalValueForOrder}")
    BigDecimal minimalValueForOrder;
    private String orderText = "";

    private CustomerInfo customerInfo;
    private TerminalReply terminalReplyInfo;

    /*
        expected format:
                      #OrderId*Order info#

        UOrderId*
     */

    public BigDecimal getTotalPriceDiscount() {
        return totalPrice.subtract(totalDiscount);
    }

    @Override
    public String toString() {

        String s = orderListToString() +
                customerInfo +
                priceToString();

        return s;

    }


    private String orderListToString() {
        StringBuilder s = new StringBuilder();

        String newLine = "\\r";

        for (Item item : orderList) {
            s.append(newLine);
            s.append(item.getName());
            int innerCounter = 0;
            for (Item subItem : item.getItemList()) {
                if (innerCounter == 0)
                    s.append(newLine);
                s.append("+");
                s.append(subItem.getName());
                s.append(newLine);
                ++innerCounter;
            }
        }
        s.append(newLine);
        return s.toString();
    }

    private String priceToString() {
        String newLine = "\\r";
        String s = "Celková cena: " + this.getTotalPriceDiscount() +
                newLine;
        return s;
    }

}
