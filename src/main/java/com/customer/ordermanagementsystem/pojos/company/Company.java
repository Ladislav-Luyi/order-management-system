package com.customer.ordermanagementsystem.pojos.company;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Company")
@Data
public class Company {
    @Id
    private Long id;

    private boolean status;

    private String statusMessage = "";

    private Date placedAt;

    @PrePersist
    void placedAt(){
        placedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        placedAt = new Date();
    }

    @Override
    public String toString() {
        final String newLine = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();

        sb.append("Podnik: ");

        if (status)
            sb.append("Otvorený");
        else
            sb.append("Zatvorený");

        sb.append(newLine);

        sb.append("Správa: ");
        sb.append(statusMessage);


        return sb.toString();
    }


}
