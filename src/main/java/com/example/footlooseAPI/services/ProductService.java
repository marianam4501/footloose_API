package com.example.footlooseAPI.services;

import com.example.footlooseAPI.dtos.AddProductDto;
import com.example.footlooseAPI.dtos.ProductModel;
import com.example.footlooseAPI.entities.ProductEntity;
import com.example.footlooseAPI.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;


    @Transactional(readOnly = true)
    public List<ProductModel> getAllProducts() {
        List<ProductEntity> products = (List<ProductEntity>) productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductModel.class))
                .collect(Collectors.toList());
    }

    public List<ProductModel> getFeaturedProducts(int quantity) {
        List<ProductEntity> products = new ArrayList<>();
        this.productRepository.findAll().forEach(products::add);

        // Shuffle the list to get a random order
        Collections.shuffle(products);

        // Limit the list to the specified quantity
        List<ProductEntity> featuredProducts = products.stream()
                .limit(quantity)
                .collect(Collectors.toList());

        return featuredProducts.stream()
                .map(product -> this.modelMapper.map(product, ProductModel.class))
                .collect(Collectors.toList());
    }

    public ProductModel getProductById(Integer id) {
        Optional<ProductEntity> product = this.productRepository.findById(id);

        if(product.isPresent()){
            return this.modelMapper.map(product, ProductModel.class);
        }

        return null;
    }

    public ProductModel addProduct(AddProductDto product) {
        ProductEntity newProduct = this.modelMapper.map(product, ProductEntity.class);
        newProduct.setStock(100);
        this.productRepository.save(newProduct);
        return this.modelMapper.map(newProduct, ProductModel.class);
    }

    public boolean addMultipleProducts(List<AddProductDto> products){
        for(AddProductDto product: products){
            ProductEntity newProduct = this.modelMapper.map(product, ProductEntity.class);
            newProduct.setStock(100);
            this.productRepository.save(newProduct);
        }
        return true;
    }

    public boolean deleteProduct(Integer id) {
        Optional<ProductEntity> productEntity = this.productRepository.findById(id);

        if(productEntity.isPresent()){
            this.productRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public ProductModel editProduct(ProductModel productModel) {
        Optional<ProductEntity> originalProductOptional = this.productRepository.findById(productModel.getId());

        if (originalProductOptional.isPresent()) {
            ProductEntity originalProduct = originalProductOptional.get();

            if (!originalProduct.getName().equals(productModel.getName())) {
                originalProduct.setName(productModel.getName());
            }
            if (!originalProduct.getDescription().equals(productModel.getDescription())) {
                originalProduct.setDescription(productModel.getDescription());
            }
            if (!originalProduct.getPrice().equals(productModel.getPrice())) {
                originalProduct.setPrice(productModel.getPrice());
            }
            if (!originalProduct.getImage().equals(productModel.getImage())) {
                originalProduct.setImage(productModel.getImage());
            }
            if (!originalProduct.getBrand().equals(productModel.getBrand())) {
                originalProduct.setBrand(productModel.getBrand());
            }
            if (!originalProduct.getSizes().equals(String.join(",", productModel.getSizes()))) {
                originalProduct.setSizes(productModel.getSizes());
            }
            if (!originalProduct.getCategory().equals(productModel.getCategory())) {
                originalProduct.setCategory(productModel.getCategory());
            }
            if (productModel.getStock() != null && !originalProduct.getStock().equals(productModel.getStock())) {
                originalProduct.setStock(productModel.getStock());
            }

            this.productRepository.save(originalProduct);
            return this.modelMapper.map(originalProduct, ProductModel.class);
        }

        return null;
    }
}
