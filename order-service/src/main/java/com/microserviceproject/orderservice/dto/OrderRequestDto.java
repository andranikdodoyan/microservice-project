package com.microserviceproject.orderservice.dto;

import com.microserviceproject.orderservice.model.OrderLineItems;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
