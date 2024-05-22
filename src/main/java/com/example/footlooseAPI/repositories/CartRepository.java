package com.example.footlooseAPI.repositories;

import com.example.footlooseAPI.entities.CartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<CartEntity, Integer> {
    //Optional<ProductEntity> findByEmail(String email);

    CartEntity findByOwnerId(Integer id);
}