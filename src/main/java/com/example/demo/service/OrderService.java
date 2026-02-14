package com.example.demo.service;

import com.example.demo.dto.request.CreateOrderReq;
import com.example.demo.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order create(CreateOrderReq req);

    Order getById(String id);

    List<Order> getAll(Pageable pageable);

    void deleteById(String id);

    void deleteAll();
}
