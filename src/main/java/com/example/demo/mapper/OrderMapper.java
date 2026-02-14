package com.example.demo.mapper;

import com.example.demo.dto.request.CreateOrderReq;
import com.example.demo.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order fromProductRequest(CreateOrderReq createProductReq);


}
