package com.denik.vy.myservice.enums;

public enum EmRating {
    G("g"),PG("pg"),PG_13("pg-13"),R("r");
    private String title;
    EmRating(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title;
    }
}
