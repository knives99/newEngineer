package com.example.project01.repository;


import com.example.project01.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    List<Product> findByNameLike(String productName);

    List<Product> findByIdIn(List<String>ids);

    boolean existsByEmail(String email);

    Optional<Product> findByUsernameAndPassword(String email,String pwd);
    List<Product> findByNameLikeIgnoreCase(String name , Sort sort);
    @Query("{'price': {'$gte': ?0, '$lte': ?1}}")
    List<Product> findByPriceBetween(int from,int to);
    @Query("{'name': {'$regex': ?0, '$options': 'i'}}")
    List<Product>findByNameLikeIgnoreCase(String name);

    @Query("{'$and': [{'price': {'$gte': ?0, '$lte': ?1}}, {'name': {'$regex': ?2, '$options': 'i'}}]}")
     List<Product> findByPriceBetweenAndNameLikeIgnoreCase(int priceFrom , int priceTo, String name, Sort sort);

    @Query(value = "{'_id': {'$in': ?0}}", count = true)
     int countByIdIn(List<String> ids);

    @Query(value = "{'_id': {'$in': ?0}}", exists = true)
     boolean existsByIdIn(List<String> ids);

    @Query(delete = true)
     void deleteByIdIn(List<String> ids);

    @Query(sort = "{'name': 1, 'price': -1}")
     List<Product> findByIdInOrderByNameAscPriceDesc(List<String> ids);



}
