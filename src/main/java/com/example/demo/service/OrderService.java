package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order create(OrderDTO req) throws JsonProcessingException;

    Order getById(String id);

    List<Order> getAll(Pageable pageable);

    void deleteById(String id);

    void deleteAll();

    void updateStatus(String id, OrderStatus status);

}
