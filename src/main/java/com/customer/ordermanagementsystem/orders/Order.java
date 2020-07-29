package com.customer.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@Component
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Long id;

    private Date placedAt;

    @ManyToMany(targetEntity=Item.class)
    private  List<Item> orderList = new ArrayList<>();

    @PrePersist
    void placedAt(){
        placedAt = new Date();
    }

    
}
