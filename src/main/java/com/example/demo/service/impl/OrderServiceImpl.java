package com.example.demo.service.impl;

import com.example.demo.dto.request.CreateOrderItemReq;
import com.example.demo.dto.request.CreateOrderReq;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.ApplicationException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order create(CreateOrderReq req) {

        Order order = orderMapper.fromProductRequest(req);
        order.setIsDeleted(false);
        int totalAmount = 0;

        if (order.getOrderItems() != null) {
            totalAmount = order.getOrderItems().stream()
                    .mapToInt(item -> item.getPrice() * item.getQuantity())
                    .sum();
        }

        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.CREATED);
        return orderRepository.save(order);
    }

    @Override
    public Order getById(String id) {

        return orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Order not found"));
    }

    @Override
    public List<Order> getAll(Pageable pageable) {

        return orderRepository.findAll();
    }

    @Override
    public void deleteById(String id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Order not found"));

        orderRepository.delete(order);
    }

    @Override
    public void deleteAll() {

        orderRepository.deleteAll();
    }
}
