package io.jayaprabahar.camunda.ecommerce.sellernotificationservice.controller;

import io.jayaprabahar.camunda.ecommerce.common.dto.CartDataDto;
import io.jayaprabahar.camunda.ecommerce.sellernotificationservice.service.SellerNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/seller-notification")
@Slf4j
public class SellerNotificationController {

    private final SellerNotificationService sellerNotificationService;

    public SellerNotificationController(SellerNotificationService sellerNotificationService) {
        this.sellerNotificationService = sellerNotificationService;
    }

    @PostMapping("/sendOrder")
    public ResponseEntity<Object> sendMessage(@RequestBody CartDataDto cartDataDto) throws IOException {
        sellerNotificationService.sendMessage(cartDataDto);
        return new ResponseEntity<>("Seller is informed", HttpStatus.ACCEPTED);
    }
}
