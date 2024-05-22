package com.example.footlooseAPI.dtos;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    public Integer id;
    public List<CartProductModel> products;
    public Integer ownerId;
    private Double subtotal;
    private Double taxes;
    private Double total;

    public CartModel() {
        this.products = new ArrayList<>();
        this.subtotal = 0.0;
        this.taxes = 0.0;
        this.total = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CartProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductModel> products) {
        this.products = products;
    }

    public Integer getOwner() {
        return ownerId;
    }

    public void setOwner(UserModel owner) {
        this.ownerId = owner.getId();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
