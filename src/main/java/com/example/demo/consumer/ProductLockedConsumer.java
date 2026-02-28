package com.example.demo.consumer;

import com.example.demo.clients.dto.ProductLockedEvent;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import static com.example.demo.enums.OrderStatus.PREPARED;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductLockedConsumer {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(topics = "product_locked")
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 2000, multiplier = 2.0),
            exclude = {NullPointerException.class, IllegalArgumentException.class}
    )
    public void handleProductLockedEvent(String productLockedEventString) throws JsonProcessingException {
        log.info("Receive order after locked product {}", productLockedEventString);
        ProductLockedEvent productLockedEvent = objectMapper.readValue(productLockedEventString, ProductLockedEvent.class);
        orderService.updateStatus(productLockedEvent.getOrderId(), PREPARED);
    }
}
