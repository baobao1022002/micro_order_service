package com.example.demo.clients.impl;

import com.example.demo.clients.ProductFilter;
import com.example.demo.common.BaseResponse;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductClientImpl implements com.example.demo.clients.ProductClient {
    private final WebClient.Builder webclientBuilder;

    @Override
    public List<ProductDTO> getProductsByIds(ProductFilter productFilter) {
        BaseResponse<List<ProductDTO>> response = webclientBuilder.build()
                .post()
                .uri("http://localhost:8888/v1/products/search")
                .bodyValue(productFilter)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ProductDTO>>>() {
                }).block();

        if (response == null || response.getData() == null) {
            throw new BusinessException("Không thể lấy thông tin sản phẩm");
        }
        return response.getData();
    }
}
