package com.example.test.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PutMapping
    ResponseEntity<OrderDto> getOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.getOrder(orderDto));
    }
}
