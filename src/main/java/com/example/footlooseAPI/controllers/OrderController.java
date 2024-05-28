package com.example.footlooseAPI.controllers;
import com.example.footlooseAPI.dtos.CreateOrderDto;
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderModel> getOrders(){
        return this.orderService.getOrders();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<OrderModel> getUserOrders(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return this.orderService.getOrdersByOwnerId(currentUser.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public OrderModel getOrderById(@PathVariable("id") Integer id){
        return this.orderService.getOrderById(id);
    }

    @PostMapping("/complete")
    @PreAuthorize("hasRole('USER')")
    public OrderModel completeOrder(@RequestBody CreateOrderDto newOrderModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return this.orderService.completeOrder(currentUser.getCart().getId(), newOrderModel);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderModel changeStatus(@RequestBody OrderModel orderModel){
        return this.orderService.changeStatus(orderModel);
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderModel adminGetOrderById(@PathVariable("id") Integer id){
        return this.orderService.getOrderById(id);
    }

}
