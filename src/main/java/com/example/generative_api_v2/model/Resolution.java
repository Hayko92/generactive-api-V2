package com.example.generative_api_v2.model;

public enum Resolution {
    HD(1), FHD(2), _4K(3);
private final int id;

    Resolution(int id) {
  this.id = id;
    }


    public int getId() {
        return id;
    }
}
