package com.example.footlooseAPI.controllers;
import com.example.footlooseAPI.dtos.CartModel;
import com.example.footlooseAPI.dtos.CartProductModel;
import com.example.footlooseAPI.entities.UserEntity;
import com.example.footlooseAPI.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cart")
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public CartModel getCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return this.cartService.getCart(currentUser.getId());
    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('USER')")
    public CartModel addProductToCart(@RequestBody CartProductModel product){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return this.cartService.addProductToCart(currentUser.getCart().getId(), product);
    }

    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasRole('USER')")
    public CartModel deleteProductFromCart(@PathVariable("id") Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return this.cartService.deleteProductFromCart(currentUser.getCart().getId(), id);
    }

    @PutMapping("/increaseQuantity")
    @PreAuthorize("hasRole('USER')")
    public CartProductModel increaseQuantity(@RequestBody CartProductModel product){
        return this.cartService.increaseQuantity(product);
    }

    @PutMapping("/decreaseQuantity")
    @PreAuthorize("hasRole('USER')")
    public CartProductModel decreaseQuantity(@RequestBody CartProductModel product){
        return this.cartService.decreaseQuantity(product);
    }

    @PutMapping("/editSize")
    @PreAuthorize("hasRole('USER')")
    public CartProductModel editSize(@RequestBody CartProductModel product){
        return this.cartService.editSize(product);
    }

}
