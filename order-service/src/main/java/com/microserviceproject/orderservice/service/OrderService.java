package com.microserviceproject.orderservice.service;

import com.microserviceproject.orderservice.dto.OrderLineItemsDto;
import com.microserviceproject.orderservice.dto.OrderRequestDto;
import com.microserviceproject.orderservice.model.Order;
import com.microserviceproject.orderservice.model.OrderLineItems;
import com.microserviceproject.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDto orderRequestDto) {
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequestDto.getOrderLineItemsDtoList().stream()
                .map(orderLineItemsDto -> mapFromDto(orderLineItemsDto))
                .toList();
        newOrder.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(newOrder);
    }

    private OrderLineItems mapFromDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
