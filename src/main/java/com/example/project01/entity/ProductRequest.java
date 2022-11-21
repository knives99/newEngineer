package com.example.project01.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductRequest {
    @NotEmpty(message =  "Product name is undefined")
    private  String name;

    @NotNull
    @Min(message = "price should be greater or equal to 0",value = 0)
    private  Integer price;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }


}
