package com.denik.vy.myservice.enums;

public enum EmRich {

    NO_CHANGE("not change"), RICH("i'm rich"), POOR("no money");
    private String title;

    EmRich(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
