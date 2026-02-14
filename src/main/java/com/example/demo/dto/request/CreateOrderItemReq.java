package com.example.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemReq {

    @NotEmpty
    private String productId;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    @Positive
    private Integer quantity;
}
