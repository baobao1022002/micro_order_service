package com.example.demo.clients;

import com.example.demo.clients.dto.LockProductDTO;
import com.example.demo.dto.ProductDTO;

import java.util.List;

public interface ProductClient {
    List<ProductDTO> getProductsByIds(ProductFilter productFilter) throws Exception;
    boolean lockProduct(LockProductDTO lockProductDTO);
}
