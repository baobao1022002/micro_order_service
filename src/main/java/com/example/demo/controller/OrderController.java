package com.example.demo.controller;

import com.example.demo.dto.request.BaseResponse;
import com.example.demo.dto.request.CreateOrderReq;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    // CREATE
    @PostMapping
    public ResponseEntity<BaseResponse<Order>> create( @RequestBody @Valid CreateOrderReq req) {

        return ResponseEntity.ok(
                new BaseResponse<>(orderService.create(req), "Success")
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Order>> getById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                new BaseResponse<>(orderService.getById(id), "Success")
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<BaseResponse<List<Order>>> getAll(@PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
                                                                Pageable pageable) {

        return ResponseEntity.ok(
                new BaseResponse<>(orderService.getAll(pageable), "Success")
        );
    }

    // DELETE BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteById(
            @PathVariable String id) {

        orderService.deleteById(id);

        return ResponseEntity.ok(
                new BaseResponse<>(null, "Deleted successfully")
        );
    }

    // DELETE ALL
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteAll() {

        orderService.deleteAll();
        return ResponseEntity.ok(
                new BaseResponse<>(null, "Deleted all successfully")
        );
    }
}
