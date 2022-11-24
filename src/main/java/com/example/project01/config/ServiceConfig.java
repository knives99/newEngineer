package com.example.project01.config;

import com.example.project01.repository.ProductRepository;
import com.example.project01.service.MailService;
import com.example.project01.service.ProductService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ServiceConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ProductService productService(ProductRepository productRepository, MailService   mailService){
        System.out.println("Product Service is created.");
        return  new ProductService(productRepository,mailService);
    }

}
