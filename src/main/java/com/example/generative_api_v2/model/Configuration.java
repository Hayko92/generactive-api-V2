package com.example.generative_api_v2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Resolution resolution;

    private List<Item> items;

    public Configuration() {
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public Configuration(Resolution resolution) {
        this.resolution = resolution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     @Transient
    public List<Item> getItems() {
        return   items;
    }

    public void setItems(List<Item> item) {
        this.items = item;
    }
}
