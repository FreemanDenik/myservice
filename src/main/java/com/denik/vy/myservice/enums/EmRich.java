package com.denik.vy.myservice.enums;

public enum EmRich {

    NO("no"), RICH("rich"), POOR("broke");
    private final String title;

    EmRich(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
