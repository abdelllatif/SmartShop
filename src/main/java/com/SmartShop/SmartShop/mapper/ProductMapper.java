package com.SmartShop.SmartShop.mapper;


import com.SmartShop.SmartShop.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ProductMapper  {

    public Product toProduct(Product product);
}
