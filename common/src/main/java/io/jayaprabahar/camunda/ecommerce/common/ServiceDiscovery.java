package io.jayaprabahar.camunda.ecommerce.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServiceDiscovery {

    CHECKOUT_SERVICE ("http://localhost:8082/checkOut/"),
    EMAIL_SERVICE ("http://localhost:8083/email/sendMail"),
    INVENTORY_SERVICE ("http://localhost:8084/inventory/checkOut/"),
    SELLER_NOTIFICATION_SERVICE ("http://localhost:8085/seller-notification/sendOrder")
    ;

    private final String url;

}
