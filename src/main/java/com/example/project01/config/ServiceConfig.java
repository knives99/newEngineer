package com.example.project01.config;

import com.example.project01.repository.ProductRepository;
import com.example.project01.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ProductService productService(ProductRepository productRepository){
        return  new ProductService(productRepository);
    }

}
