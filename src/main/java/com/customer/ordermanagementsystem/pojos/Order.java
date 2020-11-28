package com.customer.ordermanagementsystem.pojos;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@Component
@SessionScope
public class Order{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Long id;

    private Date placedAt;

    private BigDecimal totalPrice = new BigDecimal(0);

    private BigDecimal totalDiscount = new BigDecimal(0);

    private BigDecimal totalPriceDiscount = new BigDecimal(0);

    private boolean isPaid = false;

    private boolean isAccepted = false;

    @Transient
    private  List<Item> orderList = new ArrayList<>();

    private  String orderText = "";

    @PrePersist
    void placedAt(){
        placedAt = new Date();
    }

    private OrderInfo orderInfo;

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

        return s.toString();

    }


    private String orderListToString(){
        StringBuilder s = new StringBuilder();
        final String newLine = System.getProperty("line.separator");

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



}
