package com.lab.cosmetics.model;

public class Cosmetics {
    private int id;
    private String name;
    private String imageUrl;
    private String description;
    private double capacity;
    private String classification;

    public Cosmetics(int id, String name, String imageUrl, String description, double capacity, String classification) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.capacity = capacity;
        this.classification = classification;
    }

    // Геттери та сеттери
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public double getCapacity() {
        return capacity;
    }

    public String getClassification() {
        return classification;
    }
}