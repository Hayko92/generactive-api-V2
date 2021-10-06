package com.example.generative_api_v2.dto;

import com.example.generative_api_v2.model.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemDTO {
    private  int id;
    @NotNull(message = "can not be NULL:custom message")
    @Size
    @NotBlank
    private  String title;
    private  String image_url;
    private  int price;
    private  String currency;
    private Configuration configuration;
    private GroupDTO parent;
    private int parentId;

    public ItemDTO() {
    }

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
    }

    public ItemDTO(String title, int price,String image_url,  String currency) {
        this.title = title;
        this.image_url = image_url;
        this.price = price;
        this.currency = currency;
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

    public String getImage_url() {
        return image_url;
    }

    public String getCurrency() {
        return currency;
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

    public GroupDTO getParent() {
        return parent;
    }

    public void setParent(GroupDTO parent) {
        this.parent = parent;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }


}
