package com.example.demo.clients.dto;

import lombok.Data;

import java.util.List;

@Data
public class LockProductDTO {
    private List<LockProductItemDTO> items;
}
