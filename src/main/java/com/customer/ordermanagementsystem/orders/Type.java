package com.customer.ordermanagementsystem.orders;


public enum Type {

    PIZZA("P"), POLIEVKA("S"), NAPOJ("N");


    private String shortName;

    private Type(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static Type fromShortName(String shortName) {
        switch (shortName) {
            case "P":
                return Type.PIZZA;

            case "S":
                return Type.POLIEVKA;

            case "N":
                return Type.NAPOJ;

            default:
                throw new IllegalArgumentException("ShortName [" + shortName
                        + "] not supported.");
        }
    }
}
