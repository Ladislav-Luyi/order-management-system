package com.customer.ordermanagementsystem.pojos.item.menu_item;

import com.customer.ordermanagementsystem.pojos.item.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "MenuItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Transient
    private String soupOrMeal;

    private String name;

    private BigDecimal price;

    private String date;

    public MenuItem(String name, BigDecimal price, String date) {
        this.soupOrMeal = soupOrMeal;
        this.name = name;
        this.price = price;
        this.date = date;
    }
}
