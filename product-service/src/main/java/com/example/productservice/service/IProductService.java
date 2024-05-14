package com.example.productservice.service;

import com.example.productservice.request.ProductRequest;
import com.example.productservice.response.ProductResponse;

public interface IProductService {

    long addProduct(ProductRequest request);

    ProductResponse getProductById(long id);

    void reduceQuantity(long id, long quantity);

    public void deleteProductById(long id);
}
