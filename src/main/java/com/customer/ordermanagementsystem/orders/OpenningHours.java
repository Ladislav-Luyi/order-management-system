package com.customer.ordermanagementsystem.orders;

import lombok.Data;
import org.springframework.stereotype.Component;

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
    private String dayDescription;
    private int openning_hours;
    private int closing_hours;


}
