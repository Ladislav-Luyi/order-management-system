package com.customer.ordermanagementsystem.pojos.company;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "OpenningHours")
@Data
public class OpenningHours {
    @Id
    private Long id;
    private String description;
    private String matcher;
    private int openning_hours;
    private int closing_hours;
    private int priority;


}
