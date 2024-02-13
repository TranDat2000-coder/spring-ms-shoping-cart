package com.example.productservice.service.impl;

import com.example.productservice.config.exception.ProductServiceCustomException;
import com.example.productservice.entity.Products;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.request.ProductRequest;
import com.example.productservice.response.ProductResponse;
import com.example.productservice.service.IProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {

        log.info("ProductServiceImpl | addProduct is called");

        Products products = Products.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(products);

        log.info("ProductServiceImpl | addProduct | Product Created");
        log.info("ProductServiceImpl | addProduct | Product Id : " + products.getId());

        return products.getId();
    }

    @Override
    public ProductResponse getProductById(long id) {

        log.info("ProductServiceImpl | getProductById is called");
        log.info("ProductServiceImpl | getProductById | Get the product for productId: {}", id);

        Products products = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given Id not found","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();

        copyProperties(products, productResponse);

        log.info("ProductServiceImpl | getProductById | productResponse :" + productResponse.toString());

        return productResponse;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {

        log.info("Reduce Quantity {} for Id: {}", quantity, id);

        Products products = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product with given not found",
                        "PRODUCR_NOT_FOUND"
                ));

        if(products.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient Quantity", "INSUFFICIENT_QUANTITY");
        }

        products.setQuantity(quantity);
        productRepository.save(products);

        log.info("Product Quantity updated Successfully");
    }

    @Override
    public void deleteProductById(long id) {

        log.info("Product id: {}", id);

        if(!productRepository.existsById(id)){
            log.info("Im in this loop {}", !productRepository.existsById(id));
            throw new ProductServiceCustomException(
                    "Product with given with Id: " + id + " not found:",
                    "PRODUCT_NOT_FOUND");
        }

        log.info("Deleting Product with id: {}", id);
        productRepository.deleteById(id);

    }
}
