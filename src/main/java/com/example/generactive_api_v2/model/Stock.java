package com.example.generactive_api_v2.model;

public class Stock extends Item {
    public Stock( String title, int price, String image_URL, Configuration configuration, String currency) {
        super( title, price, image_URL, configuration, currency);
    }


    public Stock() {
        super();
    }
}

