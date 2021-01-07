package com.customer.ordermanagementsystem.pojos.order;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuItem;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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

    @Transient
    private  List<Item> orderList = new ArrayList<>();

    @Transient
    private  List<MenuItem> orderListMenu = new ArrayList<>();

    @Transient
    @Value("${minimalValueForOrder}")
    BigDecimal minimalValueForOrder;

    @Column(columnDefinition = "TEXT")
    private  String orderText = "";

    @PrePersist
    void placedAt(){
        placedAt = new Date();
    }

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
