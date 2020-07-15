package com.customer.ordermanagementsystem.orders;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Items")
@Data
//@RequiredArgsConstructor
//@NoArgsConstructor,
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public  Long id;
    public  String name;


    public  Type type;
    public  Float price;

    public Item(Long id, String name, Type type, Float price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Item(){

    }

    public static enum Type {
        PIZZA, POLIEVKA, NAPOJ
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Float getPrice() {
        return price;
    }
}
