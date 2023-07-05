package com.microserviceproject.orderservice.controller;

import com.microserviceproject.orderservice.dto.OrderRequestDto;
import com.microserviceproject.orderservice.service.OrderService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public String placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.placeOrder(orderRequestDto);
        return "Order placed successfully";
    }
}
