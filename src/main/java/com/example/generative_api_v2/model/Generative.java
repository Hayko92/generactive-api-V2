package com.example.generative_api_v2.model;

import javax.persistence.*;

@Entity
@Table(name = "generactive_item")
public class Generative extends Item {

    @Column(name = "complexity")
    private Double complexity;

    public Generative() {
        super();
        complexity = 0d;
    }

    public Generative(double complexity) {
        this.complexity = complexity;
    }

    public Generative(String title, String image_url, int price, String currency, Group parent, double complexity) {
        super(title, image_url, price, currency, parent);
        this.complexity = complexity;
    }

    public Generative(String title, int price, String image_url, double complexity, String currency) {
        super(title, image_url, price, currency);
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
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

    public Double getComplexity() {
        return complexity;
    }

}
