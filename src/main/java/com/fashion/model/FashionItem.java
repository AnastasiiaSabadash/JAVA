package com.fashion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class FashionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Змінено з int на Long

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    public FashionItem() {}

    public FashionItem(String name, String description, String brand) {
        this.name = name;
        this.description = description;
        this.brand = brand;
    }

    // Геттери та сеттери теж з типом Long
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    @Override
    public String toString() {
        return "FashionItem{id=" + id + ", name='" + name + "', description='" + description + "', brand='" + brand + "'}";
    }
}