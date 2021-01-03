package com.customer.ordermanagementsystem.pojos.item.menu_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MenuEditDTO {

    private String specificDate;

    private String soup1;
    private String soup1price;

    private String meal1;
    private String meal1price;

    private String meal2;
    private String meal2price;

    private String meal3;
    private String meal3price;

    private String meal4;
    private String meal4price;

}
