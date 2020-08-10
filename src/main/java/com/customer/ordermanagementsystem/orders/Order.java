package com.customer.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@Component
@SessionScope
public class Order{
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
