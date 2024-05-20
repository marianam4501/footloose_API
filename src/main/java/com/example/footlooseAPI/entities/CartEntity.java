package com.example.footlooseAPI.entities;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "cart")
@Entity
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProductEntity> products;

    @Column(nullable = false)
    private Long subtotal;

    @Column(nullable = false)
    private Long taxes;

    @Column(nullable = false)
    private Long total;

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
        for(CartProductEntity product: products){
            this.subtotal += product.getProduct().getPrice();
        }
        this.taxes = (long) (this.subtotal * 0.13);
        this.total = this.subtotal - this.taxes;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
        this.taxes = (long) (this.subtotal * 0.13);
        this.total = this.subtotal - this.taxes;
    }

    public Long getTaxes() {
        return taxes;
    }

    public void setTaxes(Long taxes) {
        this.taxes = taxes;
        this.total = this.subtotal - this.taxes;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
