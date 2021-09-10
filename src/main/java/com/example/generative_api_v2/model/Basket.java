package com.example.generative_api_v2.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private final List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void printPrice() {
        System.out.println("Items in your basket: " + this.getItems());
        int summ = 0;
        for (Item item : this.getItems()) {
            summ += item.calculatePrice(item.getConfiguration());
        }
        System.out.println("Sum of your Basket:" + summ);
    }
}
