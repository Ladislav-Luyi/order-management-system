package com.customer.ordermanagementsystem.domain.item;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Items")
@NoArgsConstructor
@SessionScope
public class Item {

    @Id
    private String id;
    private Type type;
    private String name;
    private String additionalInfo;
    private BigDecimal price;
    private String date;
    private List<Item> itemList = new ArrayList<>();

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String itemListToString() {
        StringBuilder stringBuilder = new StringBuilder();

        int counter = 0;

        for (Item item : itemList) {
            if (counter == 0) {
                stringBuilder.append("(");
                stringBuilder.append(item.name.toLowerCase());
            } else
                stringBuilder.append(", ").append(item.name.toLowerCase());

            counter++;
        }
        if (counter > 0)
            stringBuilder.append(")");

        return stringBuilder.toString();

    }


}
