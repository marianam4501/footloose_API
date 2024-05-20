package com.example.footlooseAPI.repositories;

import com.example.footlooseAPI.entities.RoleEnum;
import com.example.footlooseAPI.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(RoleEnum name);
}