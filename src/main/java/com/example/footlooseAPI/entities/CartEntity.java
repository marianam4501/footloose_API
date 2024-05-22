package com.example.footlooseAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Table(name = "cart")
@Entity
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @OneToOne // @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity owner;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REFRESH, orphanRemoval = true, fetch = FetchType.EAGER)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CartProductEntity> products;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double taxes;

    @Column(nullable = false)
    private Double total;

    public CartEntity() {
        this.products = new ArrayList<CartProductEntity>();
        this.subtotal = (double) 0;
        this.taxes = (double) 0;
        this.total = (double) 0;
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
        recalculateCartTotals();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
        this.taxes = this.subtotal * 0.13;
        this.total = this.subtotal - this.taxes;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
        this.total = this.subtotal - this.taxes;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void addProduct(CartProductEntity product) {
        products.add(product);
        recalculateCartTotals();
    }

    public void removeProduct(CartProductEntity product) {
//        products.remove(product);
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getId().equals(product.getId())){
                products.remove(i);
            }
        }
        recalculateCartTotals();
    }

    public void recalculateCartTotals() {
        this.subtotal = 0.0;
        for (CartProductEntity product : this.products) {
            this.subtotal += product.getProduct().getPrice() * product.getQuantity();
        }
        this.taxes = this.subtotal * 0.13;
        this.total = this.subtotal + this.taxes;
    }
}
