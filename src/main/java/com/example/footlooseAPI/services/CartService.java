package com.example.footlooseAPI.services;

import com.example.footlooseAPI.dtos.CartModel;
import com.example.footlooseAPI.dtos.CartProductModel;
import com.example.footlooseAPI.dtos.ProductModel;
import com.example.footlooseAPI.entities.CartEntity;
import com.example.footlooseAPI.entities.CartProductEntity;
import com.example.footlooseAPI.entities.ProductEntity;
import com.example.footlooseAPI.entities.UserEntity;
import com.example.footlooseAPI.repositories.CartProductRepository;
import com.example.footlooseAPI.repositories.CartRepository;
import com.example.footlooseAPI.repositories.ProductRepository;
import com.example.footlooseAPI.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public CartModel getCart(Integer ownerId) {
        List<CartEntity> carts = (List<CartEntity>) this.cartRepository.findAll();
        for(CartEntity cart: carts){
            if(cart.getOwner().getId().equals(ownerId)){
                Optional<UserEntity> owner = this.userRepository.findById(ownerId);
                if(owner.isPresent()){
                    cart.setOwner(owner.get());
                }
                return this.modelMapper.map(cart, CartModel.class);
            }
        }
        return null;
    }

    public CartModel addProductToCart(Integer cartId, CartProductModel cartProductModel){
        boolean newProduct = true;
        Optional<CartEntity> myCart = this.cartRepository.findById(cartId);
        if(myCart.isPresent()){
            Optional<ProductEntity> product = this.productRepository.findById(cartProductModel.getProduct().getId());
            CartProductEntity cartProductEntity = new CartProductEntity();
            cartProductEntity.setProduct(product.get());
            cartProductEntity.setCart(myCart.get());
            cartProductEntity.setSize(cartProductModel.getSize());
            cartProductEntity.setQuantity(cartProductModel.getQuantity());
            for(CartProductEntity cartProduct: myCart.get().getProducts()){
                if(cartProduct.getProduct().getId().equals(cartProductModel.getProduct().getId()) && cartProduct.getSize().equals(cartProductModel.getSize())){
                    Integer newQuantity = cartProduct.getQuantity();
                    cartProduct.setQuantity(newQuantity+cartProductModel.getQuantity());
                    cartProductEntity = cartProduct;
                    newProduct = false;
                    myCart.get().recalculateCartTotals();
                }
            }
            cartProductEntity = this.cartProductRepository.save(cartProductEntity);
            if(newProduct){
                myCart.get().addProduct(cartProductEntity);
            }
            this.cartRepository.save(myCart.get());
            return this.modelMapper.map(myCart, CartModel.class);
        } else {
            return null;
        }
    }

    public CartModel deleteProductFromCart(Integer cartId, Integer id){
        Optional<CartProductEntity> cartProductEntity = this.cartProductRepository.findById(id);
        if(cartProductEntity.isPresent()){
            Optional<CartProductEntity> product = this.cartProductRepository.findById(id);
            this.cartProductRepository.delete(product.get());
            Optional<CartEntity> myCart = this.cartRepository.findById(cartId);
            myCart.get().recalculateCartTotals();
            this.cartRepository.save(myCart.get());
            return this.modelMapper.map(myCart.get(), CartModel.class);
        } else {
            return null;
        }
    }

    public CartModel increaseQuantity(CartProductModel product) {
        Optional<CartProductEntity> cartProduct = this.cartProductRepository.findById(product.getId());

        if(cartProduct.isPresent()){
            CartEntity cart = cartProduct.get().getCart();
            int quantity = cartProduct.get().getQuantity();
            cartProduct.get().setQuantity(quantity + 1);
            cart.recalculateCartTotals();
            cart = this.cartRepository.save(cart);
            this.cartProductRepository.save(cartProduct.get());
            return this.modelMapper.map(cart, CartModel.class);
        }
        return null;
    }

    public CartModel decreaseQuantity(CartProductModel product) {
        Optional<CartProductEntity> cartProduct = this.cartProductRepository.findById(product.getId());
        if(cartProduct.isPresent()){
            CartEntity cart = cartProduct.get().getCart();
            int quantity = cartProduct.get().getQuantity();
            if(quantity - 1 > 0){
                cartProduct.get().setQuantity(quantity - 1);
                cartProduct = Optional.of(this.cartProductRepository.save(cartProduct.get()));
                cart.recalculateCartTotals();
                cart = this.cartRepository.save(cart);
                return this.modelMapper.map(cart,CartModel.class);
            } else {
                this.cartProductRepository.deleteById(cartProduct.get().getId());
                cart.recalculateCartTotals();
                cart = this.cartRepository.save(cart);
                return this.modelMapper.map(cart,CartModel.class);
            }

        }
        return null;
    }

    public CartProductModel editSize(CartProductModel product) {
        Optional<CartProductEntity> cartProduct = this.cartProductRepository.findById(product.getId());

        if(cartProduct.isPresent()){
            cartProduct.get().setSize(product.getSize());
            return this.modelMapper.map(this.cartProductRepository.save(cartProduct.get()),CartProductModel.class);
        }
        return null;
    }
}
