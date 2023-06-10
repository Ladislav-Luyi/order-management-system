package com.customer.ordermanagementsystem.domain.company;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("OpenningHours")
@Data
public class OpenningHours {
    @Id
    private String id;
    private String description;
    private String matcher;
    private int openning_hours;
    private int closing_hours;
    private int priority;
}
