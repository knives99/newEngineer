package com.example.project01.converter;

import com.example.project01.entity.Product;
import com.example.project01.entity.ProductRequest;

public class ProductConverter {

    private  ProductConverter() {

    }

    public static Product toProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return product;
    }
}
