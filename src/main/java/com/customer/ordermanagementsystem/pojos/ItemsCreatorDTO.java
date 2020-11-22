package com.customer.ordermanagementsystem.pojos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemsCreatorDTO {
    private List<Item> items;

    // default and parameterized constructor

    public ItemsCreatorDTO() {
    }

    public ItemsCreatorDTO(List<Item> items) {
        this.items = items;
    }



    public void addItem(Item item) {
        this.items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
