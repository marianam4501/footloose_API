package com.example.footlooseAPI.dtos;

import java.util.List;

public class WishModel {
    private Integer id;
    private List<ProductModel> wishList;
    private UserModel owner;

    public WishModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ProductModel> getWishList() {
        return wishList;
    }

    public void setWishList(List<ProductModel> wishList) {
        this.wishList = wishList;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }
}
