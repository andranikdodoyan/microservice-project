package com.microserviceproject.productservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microserviceproject.productservice.dto.ProductRequestDto;
import com.microserviceproject.productservice.model.Product;
import com.microserviceproject.productservice.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @Autowired
    private ProductRepository productRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    private final String description = "Phone";
    private final String id = "1000";
    private final String name = "IPhone 14";

    private final String url = "/api/product";

    private final int price = 1000;


    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Test
    void shouldReturnAllProducts() throws Exception {
        productRepository.deleteAll();
        Product newProduct = Product.builder()
                .id(id)
                .description(description)
                .price(new BigDecimal(price))
                .name(name)
                .build();
        String newProductAsJson = objectMapper.writeValueAsString(newProduct);
        productRepository.save(newProduct);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(new StringBuilder().append("[").append(newProductAsJson).append("]").toString(),
                result.getResponse().getContentAsString());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        productRepository.deleteAll();
        ProductRequestDto newProductRequestDto = createProductRequestDto();
        mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequestDto)))
                .andExpect(status().isOk());
        Assert.assertEquals(productRepository.findAll().size(), 1);


    }

    private ProductRequestDto createProductRequestDto() {
        return ProductRequestDto.builder()
                .description(description)
                .price(new BigDecimal(price))
                .name(name)
                .build();
    }

}
