package com.microserviceproject.inventoryservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class Inventory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String skuCode;
    private Integer quantity;
}
