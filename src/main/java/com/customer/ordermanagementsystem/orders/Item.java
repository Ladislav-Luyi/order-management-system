package com.customer.ordermanagementsystem.orders;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Item {
    private final Long id;
    private final String name;
    private final Type type;
    private final Float price;


    public static enum Type {
        PIZZA, POLIEVKA, NAPOJ
    }

}
