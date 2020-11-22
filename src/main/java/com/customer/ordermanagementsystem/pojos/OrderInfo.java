package com.customer.ordermanagementsystem.pojos;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;


@Data
@Component
@Embeddable
public class OrderInfo {
//    @NotNull
//    @Size(min=5, max=30)
    private String name;

//    @NotNull
//    @Size(min=5, max=100)
    private String street;

//    @NotNull
//    @Size(min=5, max=100)
    private String city;

//    @NotNull
//    @Size(min=5, max=100)
    private String telephoneNumber;

}