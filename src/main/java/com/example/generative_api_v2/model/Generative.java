package com.example.generative_api_v2.model;

import javax.persistence.*;

@Entity
@Table(name = "generactive_item")
public class Generative extends Item {



    @Column(name = "complexity")
    private double complexity;

    public Generative(String title, int price, String image_URL, double complexity, String currency) {
        super(title, price, image_URL, currency);
        this.complexity = complexity;
    }

    public Generative(String title, String image_url, int price, String currency, int parent, Configuration configuration, double complexity) {
        super(title, image_url, price, currency, parent, configuration);
        this.complexity = complexity;
    }

    public Generative(double complexity) {
        this.complexity = complexity;
    }

    public Generative(String title, int price, String image_url, Configuration configuration, double complexity, String currency) {
        super(title, image_url, price, currency, configuration);
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    public Generative() {
        super();
        complexity = 0;
    }

    @Override
    public double calculatePrice(Configuration configuration) {
        switch (configuration.getResolution()) {
            case HD:
                return this.getPrice() * complexity;
            case FHD:
                return this.getPrice() * 2 * complexity;
            case _4K:
                return this.getPrice() * 4 * complexity;
        }
        return 0;
    }


    public double getComplexity() {
        return complexity;
    }
}
