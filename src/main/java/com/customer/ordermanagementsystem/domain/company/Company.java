package com.customer.ordermanagementsystem.domain.company;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Company")
@Data
public class Company {
    @Id
    private String id;
    private String name = "Default company name";
    private boolean status;
    private String statusMessage = "";

    @Override
    public String toString() {
        return "Podnik: %s%sSpráva: %s".formatted(status ? "Otvorený" : "Zatvorený", System.getProperty("line.separator"), statusMessage);
    }
}
