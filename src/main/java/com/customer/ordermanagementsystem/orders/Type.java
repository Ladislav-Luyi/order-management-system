package com.customer.ordermanagementsystem.orders;


public enum Type {

    PIZZA_NORMAL("PIZZA_NORMAL"), PIZZA_BIG("PIZZA_BIG"), PIZZA_SMALL("PIZZA_SMALL"), PIZZA_SPECIAL("PIZZA_SPECIAL"),
    POLIEVKA("POLIEVKA"),      SALAT("SALAT"),    OMACKA("OMACKA"),   POCHUTINA("POCHUTINA"),
    BAGETA("BAGETA"), BURGER("BURGER"), POMIESANE_JEDLA("POMIESANE_JEDLA"), CESTOVINA("CESTOVINA"),
    GURMANSKE("GURMANSKE"), KLASIKA("KLASIKA"), FIT("FIT"), RIZOTO("RIZOTO"), SLADKOST("SLADKOST"),
    PRILOHA("PRILOHA"), NAPOJ("NAPOJ"), DOPLNOK("DOPLNOK");


    private String shortName;

    Type(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static Type fromShortName(String shortName) {
        switch (shortName) {
            case "PIZZA_NORMAL":
                return Type.PIZZA_NORMAL;

            case "PIZZA_BIG":
                return Type.PIZZA_BIG;

            case "PIZZA_SMALL":
                return Type.PIZZA_SMALL;

            case "PIZZA_SPECIAL":
                return Type.PIZZA_SPECIAL;

            case "POLIEVKA":
                return Type.POLIEVKA;

            case "SALAT":
                return Type.SALAT;

            case "OMACKA":
                return Type.OMACKA;

            case "POCHUTINA":
                return Type.POCHUTINA;

            case "BAGETA":
                return Type.BAGETA;

            case "BURGER":
                return Type.BURGER;

            case "POMIESANE_JEDLA":
                return Type.POMIESANE_JEDLA;

            case "CESTOVINA":
                return Type.CESTOVINA;

            case "GURMANSKE":
                return Type.GURMANSKE;

            case "KLASIKA":
                return Type.KLASIKA;

            case "FIT":
                return Type.FIT;

            case "RIZOTO":
                return Type.RIZOTO;

            case "SLADKOST":
                return Type.SLADKOST;

            case "PRILOHA":
                return Type.PRILOHA;

            case "NAPOJ":
                return Type.NAPOJ;

            case "DOPLNOK":
                return Type.DOPLNOK;

            default:
                throw new IllegalArgumentException("ShortName [" + shortName
                        + "] not supported.");
        }
    }
}
