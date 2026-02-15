package com.example.demo.service.impl;

import com.example.demo.clients.ProductFilter;
import com.example.demo.clients.impl.ProductClientImpl;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClientImpl productClientImpl;

    @Override
    public Order create(OrderDTO orderDto) {

        List<String> productIds = orderDto.getOrderItems().stream()
                .map(OrderItemDTO::getProductId)
                .distinct()
                .toList();

        List<ProductDTO> products = productClientImpl.getProductsByIds(new ProductFilter(productIds)).stream().toList();

        Map<String, ProductDTO> productPriceMap = new HashMap<>();
        products.forEach(p -> {
            productPriceMap.put(p.getId(), p);
        });

        Order order = orderMapper.fromProductRequest(orderDto);
        order.setIsDeleted(false);

        int totalAmount = 0;

        if (order.getOrderItems() != null) {
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItem.setIsDeleted(false);
                orderItem.setOrder(order);
                ProductDTO product = productPriceMap.get(orderItem.getProductId());
                if (product == null) {
                    throw new BusinessException("Product with id " + orderItem.getProductId() + " not existed");
                }

                if (orderItem.getQuantity() > product.getStock()) {
                    throw new BusinessException("Product " + orderItem.getProductId() + " not enough");
                }

                totalAmount += (product.getPrice() * orderItem.getQuantity());
            }
        }


        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }

    @Override
    public Order getById(String id) {

        return orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found"));
    }

    @Override
    public List<Order> getAll(Pageable pageable) {

        return orderRepository.findAll();
    }

    @Override
    public void deleteById(String id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found"));

        orderRepository.delete(order);
    }

    @Override
    public void deleteAll() {

        orderRepository.deleteAll();
    }
}
