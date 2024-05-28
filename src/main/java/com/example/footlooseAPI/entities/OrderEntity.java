package com.example.footlooseAPI.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "orders")
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CartProductEntity> products;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double taxes;

    @Column(nullable = false)
    private Double total;

    // Shipping Information
    @Column(nullable = false)
    private String address;

    @Column
    private String address2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String zipCode;

    // Credit Card Information
    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardName;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String cvv;

    @Column
    private String orderStatus;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getStatus() {
        return orderStatus;
    }

    public void setStatus(String status) {
        this.orderStatus = status;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
