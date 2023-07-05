package com.microserviceproject.productservice.service;

import com.microserviceproject.productservice.dto.ProductRequestDto;
import com.microserviceproject.productservice.dto.ProductResponseDto;
import com.microserviceproject.productservice.model.Product;
import com.microserviceproject.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDto productRequestDto) {
        Product newProduct = Product.builder().
                name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .build();
        productRepository.save(newProduct);

        log.info("Created product: {}", newProduct);
    }

    public List<ProductResponseDto> getAllProducts() {
        List<ProductResponseDto> products = productRepository.findAll().stream()
                .map(product -> ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build())
                .toList();
        log.info("Returned overall {} products", products.size());
        return products;
    }
}
