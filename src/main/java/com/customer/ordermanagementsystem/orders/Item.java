package com.customer.ordermanagementsystem.orders;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Items")
@Data
//@RequiredArgsConstructor
//@NoArgsConstructor,
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;


    private Type type;
    private Float price;

    @Column(nullable = true)
    @ManyToMany(targetEntity=Item.class)
    private  List<Item> itemList;

    public Item(Long id, String name, Type type, Float price, List<Item> itemList) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.itemList = itemList;
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
