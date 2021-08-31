package com.example.generactive_api_v2.model.dto;

import com.example.generactive_api_v2.model.Configuration;
import com.example.generactive_api_v2.model.Group;
import com.example.generactive_api_v2.model.Resolution;
import com.example.generactive_api_v2.util.ItemIdGenerator;

public class ItemDTO {
    private  int id;
    private  String title;
    private  String image_url;
    private  int price;
    private  String currency;
    private Group parent;
    private Configuration configuration;


    public ItemDTO(String title, int price, String image_url, Configuration configuration, String currency) {
        this.title = title;
        this.price = price;
        this.currency = currency;
        this.image_url = image_url;
        this.configuration = configuration;
    }

    public ItemDTO(  String title, int price, String image_url) {
        this.title = title;
        this.price = price;
        this.image_url = image_url;
        this.currency = "USD";
        this.configuration = new Configuration(Resolution.HD);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ItemDTO() {
        this.currency = "USD";
        this.configuration = new Configuration(Resolution.HD);
    }

    public String getImage_url() {
        return image_url;
    }

    public String getCurrency() {
        return currency;
    }

    public Group getParent() {
        return parent;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

}
