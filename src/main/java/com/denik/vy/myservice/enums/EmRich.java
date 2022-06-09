package com.denik.vy.myservice.enums;

public enum EmRich {

    NO_CHANGE("no"), RICH("rich"), POOR("broke");
    private String title;

    EmRich(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
