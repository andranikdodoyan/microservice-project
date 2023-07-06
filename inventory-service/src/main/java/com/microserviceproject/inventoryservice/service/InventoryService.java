package com.microserviceproject.inventoryservice.service;

import com.microserviceproject.inventoryservice.dto.InventoryResponseDto;
import com.microserviceproject.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private InventoryRepository inventoryRepository;

    public List<InventoryResponseDto> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory -> InventoryResponseDto.builder()
                        .isInStock(inventory.getQuantity() > 0)
                        .skuCode(inventory.getSkuCode())
                        .build())
                .toList();
    }
}
