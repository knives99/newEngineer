package com.example.project01.controller;


import com.example.project01.entity.Product;
import com.example.project01.entity.ProductQueryParameter;
import com.example.project01.entity.ProductRequest;
import com.example.project01.entity.ProductResponse;
import com.example.project01.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController
@RequestMapping(produces = "application/json",value = "/products")
public class ProductController2 {

    private  final List<Product> productDB = new  ArrayList<>();

    @Autowired
    private ProductService productService;


    @PostConstruct
    private  void initDB(){
        productDB.add(new Product("b001","1234",380));
        productDB.add(new Product("b002","2",30));
        productDB.add(new Product("b003","1",38));

    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") String id){
        ProductResponse product = productService.getProductResponse(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("")
    public  ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request){
            Product product = productService.createProduct(request);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(product.getId()).toUri();

            return  ResponseEntity.created(location).body(product);
    };

    @PutMapping("/{id}")
    public  ResponseEntity<Product> replaceProduct(@PathVariable("id") String id,@Valid @RequestBody ProductRequest request){

        Product product = productService.replaceProduct(id,request);
        return ResponseEntity.ok(product);

    };


    @DeleteMapping("/{id}")
    public  ResponseEntity<Product> deleteProduct(@PathVariable("id") String id){
            productService.deleteProduct(id);
            return  ResponseEntity.noContent().build();
    }



    @GetMapping("")
    public  ResponseEntity<List<Product>> getProducts(@ModelAttribute ProductQueryParameter param) {
            List<Product> products = productService.getProducts(param);
            return  ResponseEntity.ok(products);
    }

    public  Comparator<Product> genSortComparator(String orderBy, String sortRule) {
        Comparator<Product> comparator = (p1, p2) -> 0;
        if (Objects.isNull(orderBy) || Objects.isNull(sortRule)) {
            return comparator;
        }
        if (orderBy.equalsIgnoreCase("price")) {
            comparator = Comparator.comparing(Product::getPrice);

        } else if (orderBy.equalsIgnoreCase("name")) {
            comparator = Comparator.comparing(Product::getName);
        }
        return  sortRule.equalsIgnoreCase("desc") ? comparator.reversed() : comparator;
    }

}
