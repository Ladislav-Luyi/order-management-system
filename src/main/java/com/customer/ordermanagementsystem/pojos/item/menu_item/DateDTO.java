package com.customer.ordermanagementsystem.pojos.item.menu_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateDTO {
    @NotNull
    @Size(min=10, message = "Na?ítaj dátum")
    private String date;
}
