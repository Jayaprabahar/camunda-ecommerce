package io.jayaprabahar.camunda.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDataDto {

    String[] orderId;
    String[] email;
    String[] address;
    String[] amount;
    String[] name;
    String[] product;

}
