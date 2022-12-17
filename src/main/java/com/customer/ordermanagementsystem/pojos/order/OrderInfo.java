package com.customer.ordermanagementsystem.pojos.order;

import lombok.Data;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


//TODO fix validations
@Data
@Component
public class OrderInfo {
    @NotNull
    @Size(min=5, max=30, message = "Zadajte prosím meno a priezvisko")
    private String name;

    @NotNull
    @Size(min=5, max=100, message = "Zadajte prosím ulicu")
    private String street;

    @NotNull
    @Digits(integer=10, fraction=0)
    @Size(min=10, max=10)
    private String telephoneNumber;

    private String payWithCardToDeliveryGuy;

    @NotNull
    @Size(min=3)
    private String agreement;

    @Size(max=256, message = "Maximálny počet znakov je 256")
    private String comment;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        String newLine = "\\r";
        s.append(newLine);
        s.append("Čas objednávky: " + new Date().toString());
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

        if (payWithCardToDeliveryGuy != null)
        if (payWithCardToDeliveryGuy.contains("true")){
            s.append("Platba kartou donáškarovi. ");
            s.append(newLine);
        }

        return s.toString();
    }

}
