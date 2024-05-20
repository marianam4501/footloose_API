package com.example.footlooseAPI.dtos;

import com.example.footlooseAPI.entities.RoleEnum;

public class RoleModel {
    public Integer id;
    public RoleEnum name;
    public String description;

    public RoleModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
