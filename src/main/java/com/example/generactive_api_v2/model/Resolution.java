package com.example.generactive_api_v2.model;

public enum Resolution {
    HD(1), FHD(2), _4K(3);
    private  int id;

    Resolution(int id) {
    this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
