package com.example.project01.service;

import com.example.project01.dao.MockProductDAO;
import com.example.project01.entity.Product;
import com.example.project01.entity.ProductQueryParameter;
import com.example.project01.exception.NotFoundException;
import com.example.project01.exception.UnprocessableEntityException;
import com.example.project01.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private MockProductDAO productDAO;


    public Product createProduct(Product request){
        boolean isDuplicate = productDAO.find(request.getId()).isPresent();
        if (isDuplicate) {
            throw  new UnprocessableEntityException("\"The id of the product is duplicated.\"");
        }
        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productDAO.insert(product);
    }

    public  Product getProduct(String id){
       return productDAO.find(id).orElseThrow(()-> new NotFoundException("\"Can't find product.\""));
    }

    public  Product replaceProduct(String  id, Product request){
            Product product = getProduct(id);
            return  productDAO.replace(id,request);
    }

    public  void  deleteProduct(String id ){
        Product product = getProduct(id);
        productDAO.delete(product.getId());
    }

    public List<Product> getProducts (ProductQueryParameter param){
        return  productDAO.find(param);
    }


}
