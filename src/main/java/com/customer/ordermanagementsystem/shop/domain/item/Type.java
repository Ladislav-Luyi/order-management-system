package com.customer.ordermanagementsystem.shop.domain.item;


public enum Type {
    PIZZA_NORMAL, PIZZA_BIG, PIZZA_SPECIAL,
    POLIEVKA, SALAT, OMACKA, POCHUTINA,
    ZEMIAK_SPECIAL, BAGETA, CESTOVINA,
    GURMANSKE, OBALOVANE, FIT, RIZOTO, SLADKOST, MASOVE_JEDLO,
    WRAP_FRITTATA, NIECO_MALE_POD_ZUB,
    PRILOHA, NAPOJ, DOPLNOK,
    PIVO, VINO, ALKOHOL;

    public static boolean isTypeDefined(String value) {
        try {
            Type.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}