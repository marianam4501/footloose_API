package com.example.footlooseAPI.entities;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "orders")
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProductEntity> products;

    @Column(nullable = false)
    private Long subtotal;

    @Column(nullable = false)
    private Long taxes;

    @Column(nullable = false)
    private Long total;

    public OrderEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public List<CartProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductEntity> products) {
        this.products = products;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getTaxes() {
        return taxes;
    }

    public void setTaxes(Long taxes) {
        this.taxes = taxes;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
