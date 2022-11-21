package com.example.project01.service;

import com.example.project01.converter.ProductConverter;
import com.example.project01.dao.MockProductDAO;
import com.example.project01.entity.Product;
import com.example.project01.entity.ProductQueryParameter;
import com.example.project01.entity.ProductRequest;
import com.example.project01.exception.NotFoundException;
import com.example.project01.exception.UnprocessableEntityException;
import com.example.project01.repository.ProductRepository;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private MockProductDAO productDAO;

    @Autowired
    ProductRepository repository;


    public Product createProduct(ProductRequest request){
//        boolean isDuplicate = productDAO.find(request.getId()).isPresent();
//        if (isDuplicate) {
//            throw  new UnprocessableEntityException("\"The id of the product is duplicated.\"");
//        }
//        Product product = new Product();
//        product.setId(request.getId());
//        product.setName(request.getName());
//        product.setPrice(request.getPrice());

        Product product  = ProductConverter.toProduct(request);
        return repository.insert(product);
    }

    public  Product getProduct(String id){
       return repository.findById(id).orElseThrow(()-> new NotFoundException("\"Can't find product.\""));
    }

    public  Product replaceProduct(String  id, ProductRequest request){
        Product oldProduct = getProduct(id);
        Product newProduct = ProductConverter.toProduct(request);
        newProduct.setId(oldProduct.getId());
            return  repository.save(newProduct);
    }

    public  void  deleteProduct(String id ){
        Product product = getProduct(id);
        repository.delete(product);
    }

    public List<Product> getProducts (ProductQueryParameter param){
        String keyWord = Optional.ofNullable(param.getKeyword()).orElse("");
        int priceFrom = Optional.ofNullable(param.getPriceFrom()).orElse(0);
        int priceTo = Optional.ofNullable(param.getPriceTo()).orElse(Integer.MAX_VALUE);
        Sort sort = genSortingStrategy(param.getOrderBy(), param.getSortRule());
        return  repository.findByPriceBetweenAndNameLikeIgnoreCase(priceFrom,priceTo,keyWord);
    }


    private Sort genSortingStrategy(String orderBy, String sortRule){
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy)&& Objects.nonNull(sortRule)){
            Sort.Direction direction = Sort.Direction.fromString(sortRule);
            sort = Sort.by(direction,orderBy);

        }
        return  sort;
    }


}
