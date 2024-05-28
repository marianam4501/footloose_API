package com.example.footlooseAPI.dtos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddProductDto {
    public Integer id;
    public String name;
    public String description;
    public Double price;
    public String image;
    public String brand;
    public String sizes;
    public String category;
    public Integer stock;

    public AddProductDto() {
        //this.sizes = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes){
        this.sizes = sizes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
