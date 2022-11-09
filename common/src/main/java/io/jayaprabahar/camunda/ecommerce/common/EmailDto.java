package io.jayaprabahar.camunda.ecommerce.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EmailDto {

    private String orderId;
    private String emailAddress;
    private EmailType type;

}
