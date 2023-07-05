package com.microserviceproject.productservice.controller;


import com.microserviceproject.productservice.dto.ProductRequestDto;
import com.microserviceproject.productservice.dto.ProductResponseDto;
import com.microserviceproject.productservice.repository.ProductRepository;
import com.microserviceproject.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public void createProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.createProduct(productRequestDto);
    }

    @GetMapping
    public @ResponseBody List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }
}
