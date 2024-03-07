package io.jayaprabahar.camunda.ecommerce.inventoryservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Inventory {

    @Id String itemName;
    int availableContent;
    Double price;
}
