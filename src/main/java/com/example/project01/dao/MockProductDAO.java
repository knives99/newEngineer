package com.example.project01.dao;

import com.example.project01.entity.Product;
import com.example.project01.entity.ProductQueryParameter;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class MockProductDAO {
    public  final List<Product> productDB = new ArrayList<>();



    @PostConstruct
    private  void initDB(){
        productDB.add(new Product("b001","1234",380));
        productDB.add(new Product("b002","2",30));
        productDB.add(new Product("b003","1",38));
        productDB.add(new Product("b004","2",30));
        productDB.add(new Product("b005","1",38));

    }

    public  Product insert(Product product){
        productDB.add(product);
        return  product;

    }

    public  Product replace(String id, Product product){
        Optional<Product> productOptional = find(id);
        productOptional.ifPresent(p -> {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
        });
        return  product;
    }
    public void delete(String id ){
        productDB.removeIf(p -> p.getId().equals(id));
    }

    public Optional<Product> find(String id){
        return  productDB.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Product> find(ProductQueryParameter param){
        String keyword = Optional.ofNullable(param.getKeyword()).orElse("");
        String orderBy = param.getOrderBy();
        String sortRule = param.getSortRule();
        Comparator<Product> comparator = genSortComparator(orderBy,sortRule);

        return  productDB.stream().filter(p -> p.getName().contains(keyword)).sorted(comparator)
                .toList();
    }

    private Comparator<Product> genSortComparator(String orderBy, String sortRule) {
        Comparator<Product> comparator = (p1, p2) -> 0;
        if (Objects.isNull(orderBy) || Objects.isNull(sortRule)) {
            return comparator;
        }

        if (orderBy.equalsIgnoreCase("price")) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if (orderBy.equalsIgnoreCase("name")) {
            comparator = Comparator.comparing(Product::getName);
        }

        return sortRule.equalsIgnoreCase("desc")
                ? comparator.reversed()
                : comparator;
    }
}
