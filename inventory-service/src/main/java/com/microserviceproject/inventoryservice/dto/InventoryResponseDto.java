package com.microserviceproject.inventoryservice.dto;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class InventoryResponseDto {
    private String skuCode;
    private boolean isInStock;
}
