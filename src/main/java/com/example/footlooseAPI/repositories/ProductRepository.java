package com.example.footlooseAPI.repositories;

import com.example.footlooseAPI.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    //Optional<ProductEntity> findByEmail(String email);
}