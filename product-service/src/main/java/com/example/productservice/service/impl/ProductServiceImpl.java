package com.example.productservice.service.impl;

import com.example.productservice.entity.Products;
import com.example.productservice.exception.ProductServiceCustomException;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.request.ProductRequest;
import com.example.productservice.response.ProductResponse;
import com.example.productservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest request) {
        Products products = Products.builder()
                .productName(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        products = productRepository.save(products);

        return products.getId();
    }

    @Override
    public ProductResponse getProductById(long id) {

        Products products = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given Id not found", "PRODUCT_NOT_FOUND"));

        return ProductResponse.builder()
                .id(products.getId())
                .productName(products.getProductName())
                .price(products.getPrice())
                .quantity(products.getQuantity())
                .build();
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        Products products = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given Id not found", "PRODUCT_NOT_FOUND"));

        if(products.getId() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient Quantity", "INSUFFICIENT_QUANTITY");
        }

        products.setQuantity(products.getQuantity() - quantity);
        productRepository.save(products);
    }

    @Override
    public void deleteProductById(long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductServiceCustomException(
                    "Product with given with Id: " + id + " not found:",
                    "PRODUCT_NOT_FOUND");
        }
        productRepository.deleteById(id);
    }
}
