package com.example.footlooseAPI.services;

import com.example.footlooseAPI.dtos.WishModel;
import com.example.footlooseAPI.entities.WishEntity;
import com.example.footlooseAPI.repositories.WishRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WishRepository wishRepository;

    public WishModel getWishList(Integer ownerId){
        return this.modelMapper.map(getWishListByOwnerId(ownerId), WishModel.class);
    }

    public WishEntity getWishListByOwnerId(Integer ownerId){
        List<WishEntity> wishLists = (List<WishEntity>) this.wishRepository.findAll();
        WishEntity myWishList = null;
        for(WishEntity wishList: wishLists){
            if(wishList.getOwner().equals(ownerId)){
                myWishList = wishList;
            }
        }
        return myWishList;
    }
}
