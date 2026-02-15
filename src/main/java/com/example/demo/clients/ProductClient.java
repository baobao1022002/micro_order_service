package com.example.demo.clients;

import com.example.demo.dto.ProductDTO;

import java.util.List;

public interface ProductClient {
    List<ProductDTO> getProductsByIds(ProductFilter productFilter) throws Exception;
}
