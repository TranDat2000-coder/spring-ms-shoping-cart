package com.example.productservice.controller;

import com.example.productservice.entity.Products;
import com.example.productservice.request.ProductRequest;
import com.example.productservice.response.ProductResponse;
import com.example.productservice.service.IProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//https://www.javaguides.net/2022/11/spring-boot-microservices-shopping-cart.html

@RestController
@RequestMapping("/product")
@Log4j2
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/insert-product")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request){

        log.info("ProductController | addProduct is called");
        log.info("ProductController | addProduct | productRequest : " + request.toString());

        long productId = productService.addProduct(request);

        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductId(@PathVariable("id") long id){

        log.info("ProductController | getProductById is called");
        log.info("ProductController | getProductById | productId : " + id);

        ProductResponse productResponse = productService.getProductById(id);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<?> reduceQuantity(@PathVariable("id") long id, @RequestParam("quantity") long quantity){

        log.info("ProductController | reduceQuantity is called");
        log.info("ProductController | reduceQuantity | productId : " + id);
        log.info("ProductController | reduceQuantity | quantity : " + quantity);

        productService.reduceQuantity(id, quantity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") long id){
        productService.deleteProductById(id);
    }
}
