package com.microserviceproject.inventoryservice;

import com.microserviceproject.inventoryservice.model.Inventory;
import com.microserviceproject.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner fillData(InventoryRepository inventoryRepository) {
		return args -> {
			inventoryRepository.deleteAll();
			Inventory inventory1 = new Inventory();
			inventory1.setQuantity(100);
			inventory1.setSkuCode("orbit_gums_watermelon");

			Inventory inventory2 = new Inventory();
			inventory2.setQuantity(0);
			inventory2.setSkuCode("orbit_gums_strawberry");

			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
			log.info("Successfully filled test Data to the Repository: \"{}\", \"{}\"", inventory1.getSkuCode(), inventory2.getSkuCode() );

		};
	}

}
