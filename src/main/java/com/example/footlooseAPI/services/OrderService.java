package com.example.footlooseAPI.services;

import com.example.footlooseAPI.dtos.OrderModel;
import com.example.footlooseAPI.entities.CartEntity;
import com.example.footlooseAPI.entities.OrderEntity;
import com.example.footlooseAPI.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class OrderService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    public boolean completeOrder(Integer ownerId, OrderModel orderModel){
        OrderEntity newOrder = this.modelMapper.map(orderModel, OrderEntity.class);
        CartEntity cart = this.modelMapper.map(this.cartService.getCart(ownerId).products, CartEntity.class);
        //newOrder.setProducts(cart.getProducts());
        newOrder.setSubtotal(cart.getSubtotal());
        newOrder.setStatus("Pending");
        if(!cart.getProducts().isEmpty()){
            this.orderRepository.save(newOrder);
            return true;
        } else {
            return false;
        }
    }

    public OrderModel changeStatus(OrderModel orderModel){
        Optional<OrderEntity> orderEntity = this.orderRepository.findById(orderModel.getId());

        if(orderEntity.isPresent()){
            orderEntity.get().setStatus(orderModel.getStatus());
            this.orderRepository.save(orderEntity.get());
            return this.modelMapper.map(orderEntity, OrderModel.class);
        }

        return null;
    }

    public List<OrderModel> getOrders() {
        List<OrderEntity> orders = (List<OrderEntity>) this.orderRepository.findAll();
        return orders.stream().map(order -> this.modelMapper.map(order, OrderModel.class)).toList();
    }
}
