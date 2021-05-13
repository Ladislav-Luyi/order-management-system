package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;

import java.math.BigDecimal;
import java.util.ArrayList;

public  class DefaultMenuService {

    protected static ArrayList<Item> getMenuEntries(String date) {
        Item polievka1 = new Item();
        polievka1.setType(Type.MENU_POLIEVKA);
        polievka1.setName("Kurací vývar s cestovinou");
        polievka1.setDate(date);
        polievka1.setPrice(new BigDecimal("1.40"));

        Item pizza1 = new Item();
        pizza1.setType(Type.MENU_JEDLO);
        pizza1.setName("450g pizza Margherita1,7");
        pizza1.setPrice(new BigDecimal("4.20"));
        pizza1.setDate(date);

        Item pizza2 = new Item();
        pizza2.setType(Type.MENU_JEDLO);
        pizza2.setName("450g Salámová pizza1,7");
        pizza2.setPrice(new BigDecimal("4.20"));
        pizza2.setDate(date);

        Item pizza3 = new Item();
        pizza3.setType(Type.MENU_JEDLO);
        pizza3.setName("450g Šunková pizza1,7");
        pizza3.setPrice(new BigDecimal("4.20"));
        pizza3.setDate(date);

        Item pizza4 = new Item();
        pizza4.setType(Type.MENU_JEDLO);
        pizza4.setName("450g Šampiňónová pizza1,7");
        pizza4.setPrice(new BigDecimal("4.20"));
        pizza4.setDate(date);

        ArrayList<Item> defaultMenuEntries = new ArrayList<>();


        defaultMenuEntries.add(polievka1);
        defaultMenuEntries.add(pizza1);
        defaultMenuEntries.add(pizza2);
        defaultMenuEntries.add(pizza3);
        defaultMenuEntries.add(pizza4);

        return defaultMenuEntries;
    }
}
