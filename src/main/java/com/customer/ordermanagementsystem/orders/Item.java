package com.customer.ordermanagementsystem.orders;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Items")
@Data
@RequiredArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private final Long id;
    private final String name;
    private final Type type;
    private final Float price;


    public static enum Type {
        PIZZA, POLIEVKA, NAPOJ
    }

}
