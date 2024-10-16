package com.example.demoapp.domains;

public class SwipeRefresh {
    private String name;
    private int imageResId;

    public SwipeRefresh(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
