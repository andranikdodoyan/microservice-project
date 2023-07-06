package com.microserviceproject.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class InventoryResponseDto {
    private String skuCode;
    private boolean isInStock;
}
