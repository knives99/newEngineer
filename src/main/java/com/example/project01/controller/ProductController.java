package com.example.project01.controller;


import com.example.project01.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
public class ProductController {


    @GetMapping("/pjdmmm/{id}")
    public Product getProduct(@PathVariable("id" ) String id){
        Product product = new Product();
        product.setId(id);
        product.setName("123");
        product.setPrice(200);

        return  product;
    }

}
