package com.customer.ordermanagementsystem.pojos.order;

import lombok.Data;
import org.springframework.stereotype.Component;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Component
@Embeddable
public class OrderInfo {
    @NotNull
    @Size(min=5, max=30)
    private String name;

//    @NotNull
//    @Size(min=5, max=100)
    private String street;


//    @NotNull
//    @Size(min=5, max=100)
    private String telephoneNumber;

    private String comment;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        String newLine = "\\r";
        s.append(newLine);
        s.append("Daľšie informácie:");
        s.append(newLine);
        s.append("Meno: " + name);
        s.append(newLine);
        s.append("Ulica: " + street);
        s.append(newLine);
        s.append("Telefónne číslo:" + telephoneNumber);
        s.append(newLine);
        s.append("Komentár: " + comment );
        s.append(newLine);

        return s.toString();
    }
}
