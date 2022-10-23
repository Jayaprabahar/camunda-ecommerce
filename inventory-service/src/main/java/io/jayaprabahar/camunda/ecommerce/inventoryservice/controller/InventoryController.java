package io.jayaprabahar.camunda.ecommerce.inventoryservice.controller;

import io.jayaprabahar.camunda.ecommerce.inventoryservice.dao.InventoryRepository;
import io.jayaprabahar.camunda.ecommerce.inventoryservice.entity.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PatchMapping("/checkOut/{itemName}")
    public ResponseEntity<Object> checkoutProduct(@PathVariable String itemName) {
        log.info("Inventory Check for {}", itemName);
        ResponseEntity<Object> responseEntity;

        Inventory inventory = CollectionUtils.firstElement(inventoryRepository.findByItemName(itemName));
        if (inventory != null && inventory.getAvailableContent() > 0) {
            inventory.setAvailableContent(inventory.getAvailableContent() - 1);
            inventoryRepository.save(inventory);

            responseEntity = new ResponseEntity<>("Order accepted", HttpStatus.ACCEPTED);
        } else {
            responseEntity = new ResponseEntity<>("No item found", HttpStatus.NO_CONTENT);
        }

        log.info("Response status {}", responseEntity);
        return responseEntity;
    }
}
