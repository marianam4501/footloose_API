package com.example.footlooseAPI.repositories;

import com.example.footlooseAPI.entities.WishEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends CrudRepository<WishEntity, Integer>{
}
