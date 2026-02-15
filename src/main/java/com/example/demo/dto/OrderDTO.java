package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotEmpty(message =  "Customer Id không được để trống")
    private String customerId;

    @NotEmpty(message = "Đơn hàng phải có ít nhất 1 sản phẩm")
    @Valid
    private List<OrderItemDTO> orderItems;
}
