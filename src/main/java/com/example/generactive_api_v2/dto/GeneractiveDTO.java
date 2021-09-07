package com.example.generactive_api_v2.dto;

public class GeneractiveDTO extends ItemDTO{
    private double complexity;

    public double getComplexity() {
        return complexity;
    }

    public GeneractiveDTO() {
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }
}
