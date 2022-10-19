package io.jayaprabahar.camunda.ecommerce.inventoryservice.controller;

import io.jayaprabahar.camunda.ecommerce.inventoryservice.dao.InventoryRepository;
import io.jayaprabahar.camunda.ecommerce.inventoryservice.entity.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping("/checkOut/{itemName}")
    public ResponseEntity<Object> checkoutProduct(@PathVariable String itemName){
        Inventory inventory = inventoryRepository.getReferenceById(itemName);

        if(inventory.getAvailableContent() > 0) {
            inventory.setAvailableContent(inventory.getAvailableContent()-1);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
