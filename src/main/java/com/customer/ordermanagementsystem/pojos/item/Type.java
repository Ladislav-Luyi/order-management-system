package com.customer.ordermanagementsystem.pojos.item;


public enum Type {
    PIZZA_NORMAL("PIZZA_NORMAL"), PIZZA_BIG("PIZZA_BIG"), PIZZA_SPECIAL("PIZZA_SPECIAL"),
    POLIEVKA("POLIEVKA"),      SALAT("SALAT"),    OMACKA("OMACKA"),   POCHUTINA("POCHUTINA"),
    ZEMIAK_SPECIAL("ZEMIAK_SPECIAL"), BAGETA("BAGETA"), CESTOVINA("CESTOVINA"),
    GURMANSKE("GURMANSKE"), OBALOVANE("OBALOVANE"), FIT("FIT"), RIZOTO("RIZOTO"), SLADKOST("SLADKOST"),
    PRILOHA("PRILOHA"), NAPOJ("NAPOJ"), DOPLNOK("DOPLNOK"),
    PIVO("PIVO"), VINO("VINO"), ALKOHOL("ALKOHOL");

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

            case "ZEMIAK_SPECIAL":
                return Type.ZEMIAK_SPECIAL;

            case "BAGETA":
                return Type.BAGETA;

            case "CESTOVINA":
                return Type.CESTOVINA;

            case "GURMANSKE":
                return Type.GURMANSKE;

            case "OBALOVANE":
                return Type.OBALOVANE;

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

            case "PIVO":
                return Type.PIVO;

            case "VINO":
                return Type.VINO;

            case "ALKOHOL":
                return Type.ALKOHOL;

            default:
                throw new IllegalArgumentException("ShortName [" + shortName
                        + "] not supported.");
        }
    }
}
