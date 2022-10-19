package io.jayaprabahar.camunda.ecommerce.inventoryservice.dao;

import io.jayaprabahar.camunda.ecommerce.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
}
