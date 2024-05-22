package com.example.footlooseAPI.dtos;

public class CartProductModel {
    public Integer id;
    public ProductModel product;
    public int quantity;
    public String size;
    public Integer cartId;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(CartModel cart) {
        this.cartId = cart.getId();
    }

    public CartProductModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
