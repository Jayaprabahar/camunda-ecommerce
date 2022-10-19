package io.jayaprabahar.camunda.ecommerce.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inventory {

    @Id String itemName;
    int availableContent;
    Double price;
}
