package com.lab.cosmetics;

public class Cosmetics {
    private String name;
    private String imageUrl;
    private String description;
    private double capacity;
    private String classification;

    public Cosmetics(String name, String imageUrl, String description, double capacity, String classification) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.description = description;
            this.capacity = capacity;
            this.classification = classification;
        }
    }
