package com.example.footlooseAPI.repositories;

import com.example.footlooseAPI.entities.CartProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends CrudRepository<CartProductEntity, Integer> {
    //Optional<ProductEntity> findByEmail(String email);

}