package com.example.footlooseAPI.services;

import com.example.footlooseAPI.dtos.CreateOrderDto;
import com.example.footlooseAPI.dtos.OrderModel;
import com.example.footlooseAPI.entities.CartEntity;
import com.example.footlooseAPI.entities.CartProductEntity;
import com.example.footlooseAPI.entities.OrderEntity;
import com.example.footlooseAPI.repositories.CartProductRepository;
import com.example.footlooseAPI.repositories.CartRepository;
import com.example.footlooseAPI.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderModel completeOrder(Integer cartId, CreateOrderDto orderModel){
        OrderEntity newOrder = this.modelMapper.map(orderModel, OrderEntity.class);
        Optional<CartEntity> cart = this.cartRepository.findById(cartId);
        if(cart.isPresent() && !cart.get().getProducts().isEmpty()){
            newOrder.setOwner(cart.get().getOwner());
            newOrder.setSubtotal(cart.get().getSubtotal());
            newOrder.setTaxes(cart.get().getTaxes());
            newOrder.setTotal(cart.get().getTotal());
            newOrder.setStatus("Pending");
            newOrder.setProducts(cart.get().getProducts());
            newOrder = this.orderRepository.save(newOrder);
            for(CartProductEntity product : newOrder.getProducts()){
                product.setCart(null);
                product.setOrder(newOrder);
                //newOrder.getProducts().add(product);
                this.cartProductRepository.save(product);
            }
            newOrder = this.orderRepository.save(newOrder);
            cart.get().getProducts().clear();
            cart.get().recalculateCartTotals();
            this.cartRepository.save(cart.get());
            return this.modelMapper.map(newOrder,OrderModel.class);
        }
        return null;
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

    public List<OrderModel> getOrdersByOwnerId(Integer ownerId){
        List<OrderEntity> orders = (List<OrderEntity>) this.orderRepository.findAll();
        List<OrderEntity> userOrders = new ArrayList<>();
        for(OrderEntity order : orders){
            if(order.getOwner().getId().equals(ownerId)){
                userOrders.add(order);
            }
        }
        return userOrders.stream().map(order -> this.modelMapper.map(order, OrderModel.class)).toList();
    }

    public OrderModel getOrderById(Integer id){
        Optional<OrderEntity> order = this.orderRepository.findById(id);
        if(order.isPresent()){
            return this.modelMapper.map(order, OrderModel.class);
        }
        return null;
    }
}
