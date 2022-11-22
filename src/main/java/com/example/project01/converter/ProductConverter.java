package com.example.project01.converter;

import com.example.project01.entity.Product;
import com.example.project01.entity.ProductRequest;
import com.example.project01.entity.ProductResponse;

public class ProductConverter {

    private  ProductConverter() {

    }

    public static Product toProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return product;
    }

    public  static ProductResponse toProductResponse(Product product){
        ProductResponse  response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        return  response;
    }
}
