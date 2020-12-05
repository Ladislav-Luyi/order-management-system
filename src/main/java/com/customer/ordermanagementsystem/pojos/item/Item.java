package com.customer.ordermanagementsystem.pojos.item;


import lombok.Data;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Items")
@Data
//@RequiredArgsConstructor
//@NoArgsConstructor,
@SessionScope
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Type type;
    private String name;
    private String additionalInfo;
    private BigDecimal price;
    private String date;


    @Transient
    private  List<Item> itemList = new ArrayList<>();

    public Item(Long id, String name, String additionalInfo, Type type, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.additionalInfo = additionalInfo;
        this.type = type;
        this.price = price;
    }

    public Item(Long id, String name,  Type type, BigDecimal price, String date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.date = date;
    }

    public Item() {
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String itemListToString(){
        StringBuilder stringBuilder = new StringBuilder();

        int counter = 0;

        for(Item item : itemList){
            if (counter == 0) {
                stringBuilder.append("(");
                stringBuilder.append(item.name.toLowerCase());
            }
            else
                stringBuilder.append(", " + item.name.toLowerCase());

            counter++;
        }
        if (counter > 0)
            stringBuilder.append(")");

        return stringBuilder.toString();

    }



}
