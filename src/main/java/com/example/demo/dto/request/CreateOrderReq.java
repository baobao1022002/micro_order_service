package com.example.demo.dto.request;

import com.example.demo.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderReq {

    @NotEmpty
    private String customerId;

    @NotEmpty
    private List<CreateOrderItemReq> orderItems;
}
