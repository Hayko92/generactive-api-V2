package com.example.generative_api_v2.model;

public class Generative extends Item {
    private double complexity;

    public Generative(String title, int price, String image_URL, Configuration configuration, double complexity, String currency) {
        super(title, price, image_URL, configuration, currency);
        this.complexity = complexity;
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
