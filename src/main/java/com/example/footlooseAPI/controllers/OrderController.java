package com.example.footlooseAPI.controllers;
import com.example.footlooseAPI.dtos.OrderModel;
import com.example.footlooseAPI.entities.UserEntity;
import com.example.footlooseAPI.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderModel> getOrders(){
        return this.orderService.getOrders();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public boolean completeOrder(@RequestBody OrderModel orderModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return this.orderService.completeOrder(currentUser.getId(), orderModel);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderModel changeStatus(@RequestBody OrderModel orderModel){
        return this.orderService.changeStatus(orderModel);
    }
}
