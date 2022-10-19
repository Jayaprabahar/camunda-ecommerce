package io.jayaprabahar.camunda.ecommerce.inventoryservice.init;

import io.jayaprabahar.camunda.ecommerce.inventoryservice.dao.InventoryRepository;
import io.jayaprabahar.camunda.ecommerce.inventoryservice.entity.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class InitialDataLoader {

    @Bean
    CommandLineRunner loadInitialInventoryData(InventoryRepository inventoryRepository) {
        return args -> {
            // encrypt password using BCryptPasswordEncoder.
            log.info("Preloading " + inventoryRepository.save(new Inventory("iPhone 11", 3, 599.99)));
            log.info("Preloading " + inventoryRepository.save(new Inventory("iPhone 12", 1, 799.99)));
            log.info("Preloading " + inventoryRepository.save(new Inventory("iPhone 13", 5, 799.99)));
        };
    }
}
