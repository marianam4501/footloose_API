package com.example.footlooseAPI.controllers;

import com.example.footlooseAPI.dtos.AddProductDto;
import com.example.footlooseAPI.dtos.ProductModel;
import com.example.footlooseAPI.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductModel> getAllProducts(){
        return this.productService.getAllProducts();
    }
    // /product/featured
    @GetMapping("/featured")
    public List<ProductModel> getFeaturedProducts(){
        return this.productService.getFeaturedProducts(10);
    }

    @GetMapping("/{id}")
    public ProductModel getProductById(@PathVariable("id") Integer id){
        return this.productService.getProductById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductModel addProduct(@RequestBody AddProductDto product){
        return this.productService.addProduct(product);
    }

    @PostMapping("/addMultiple")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean addProduct(@RequestBody List<AddProductDto> products){
        return this.productService.addMultipleProducts(products);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteProduct(@PathVariable("id") Integer id){
        return this.productService.deleteProduct(id);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductModel editProduct(@RequestBody ProductModel product){
        return this.productService.editProduct(product);
    }

}
