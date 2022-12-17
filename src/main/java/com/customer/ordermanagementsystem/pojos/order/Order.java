package com.customer.ordermanagementsystem.pojos.order;

import com.customer.ordermanagementsystem.pojos.item.Item;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Component
@SessionScope
@Document("Orders")
public class Order{
    @Id
    private  Long id;

    public Order(){
       Date date = new Date();
       long timeMilli = date.getTime();
       id = timeMilli;
       placedAt = new Date();
    }

    private Date placedAt;
    private BigDecimal totalPrice = new BigDecimal(0);
    private BigDecimal totalDiscount = new BigDecimal(0);
    private BigDecimal totalPriceDiscount = new BigDecimal(0);
    private boolean isPaid = false;
    private  List<Item> orderList = new ArrayList<>();

    @Value("${minimalValueForOrder}")
    BigDecimal minimalValueForOrder;
//TODO nevies co s tym zatial
//    @Column(columnDefinition = "TEXT")
    private  String orderText = "";

    private OrderInfo orderInfo;
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

        StringBuilder s = new StringBuilder();
        s.append(orderListToString());
        s.append(orderInfo);
        s.append(priceToString());

        return s.toString();

    }


    private String orderListToString(){
        StringBuilder s = new StringBuilder();

        String newLine = "\\r";

        for(Item item : orderList) {
            s.append(newLine);
            s.append(item.getName());

            if (item.getItemList().size()>0);
            int innerCounter = 0;
            for (Item subItem : item.getItemList()){
                    if(innerCounter == 0)
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

    private String priceToString(){
        StringBuilder s = new StringBuilder();

        String newLine = "\\r";
        s.append("Celková cena: " + this.getTotalPriceDiscount());
        s.append(newLine);
        return s.toString();
    }
    
}
