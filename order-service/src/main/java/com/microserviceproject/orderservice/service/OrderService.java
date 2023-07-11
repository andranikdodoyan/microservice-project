package com.microserviceproject.orderservice.service;

import com.microserviceproject.orderservice.dto.OrderLineItemsDto;
import com.microserviceproject.orderservice.dto.OrderRequestDto;
import com.microserviceproject.orderservice.model.Order;
import com.microserviceproject.orderservice.model.OrderLineItems;
import com.microserviceproject.orderservice.repository.OrderRepository;
import com.microserviceproject.common.dto.InventoryResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequestDto orderRequestDto) {
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequestDto.getOrderLineItemsDtoList().stream()
                .map(this::mapFromDto)
                .toList();
        newOrder.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodesToBeChecked = newOrder.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

//        Call Inventory Service
        InventoryResponseDto[] inventoryResponseDtoArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodesToBeChecked).build())
                .retrieve()
                .bodyToMono(InventoryResponseDto[].class)
                .block();

        if (Arrays.stream(inventoryResponseDtoArray).allMatch(InventoryResponseDto::isInStock)) {
            orderRepository.save(newOrder);
        } else {
            throw new IllegalArgumentException("Some items are not in stock");
        }

    }

    private OrderLineItems mapFromDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
