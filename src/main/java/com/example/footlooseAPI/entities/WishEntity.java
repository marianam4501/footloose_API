package com.example.footlooseAPI.entities;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "wish")
@Entity
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "wish_product",
            joinColumns = @JoinColumn(name = "wish_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> wishList;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private UserEntity owner;
}
