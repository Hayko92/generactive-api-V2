package com.example.generative_api_v2.model;

import javax.persistence.Entity;


public class Stock extends Item {
    public Stock( String title, int price, String image_URL, String currency) {
        super( title, price, image_URL, currency);
    }


    public Stock() {
        super();
    }


    public Stock(String title, int price, String image_url, Configuration configuration, String currency) {

    }
}

