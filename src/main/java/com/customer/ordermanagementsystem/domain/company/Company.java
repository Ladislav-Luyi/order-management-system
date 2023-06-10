package com.customer.ordermanagementsystem.domain.company;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Company")
@Data
public class Company {
    @Id
    private String id;
    private String name = "1";
    private boolean status;
    private String statusMessage = "";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Podnik: ");
        sb.append ( status ? "Otvorený" : "Zatvorený");
        sb.append( System.getProperty("line.separator") );
        sb.append("Správa: ");
        sb.append(statusMessage);
        return sb.toString();
    }
}
