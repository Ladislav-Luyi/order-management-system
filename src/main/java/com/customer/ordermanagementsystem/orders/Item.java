package com.customer.ordermanagementsystem.orders;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Item {
    private final Long id;
    private final String name;
    private int quantity;
    private final Type type;



    public static enum Type {
        PIZZA, POLIEVKA, NAPOJ
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
