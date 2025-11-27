package com.SmartShop.SmartShop.mapper;

import com.SmartShop.SmartShop.dto.ProductRequest;
import com.SmartShop.SmartShop.dto.ProductResponse;
import com.SmartShop.SmartShop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product toProduct(ProductRequest request);
    ProductResponse toProductResponse(Product product);

}
