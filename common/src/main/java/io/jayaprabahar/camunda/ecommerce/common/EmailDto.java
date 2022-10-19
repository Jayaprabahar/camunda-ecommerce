package io.jayaprabahar.camunda.ecommerce.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailDto {

    private String orderId;
    private String emailAddress;
    private EmailType type;

}
