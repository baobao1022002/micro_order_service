package com.example.demo.mapper;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order fromOrderRequest(OrderDTO createProductReq);


}
