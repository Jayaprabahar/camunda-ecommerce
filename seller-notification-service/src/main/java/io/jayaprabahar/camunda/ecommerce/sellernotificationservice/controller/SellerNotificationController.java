package io.jayaprabahar.camunda.ecommerce.sellernotificationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jayaprabahar.camunda.ecommerce.common.dto.CartDataDto;
import io.jayaprabahar.camunda.ecommerce.sellernotificationservice.service.SellerNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller-notification")
@Slf4j
public class SellerNotificationController {

    private final SellerNotificationService sellerNotificationService;

    public SellerNotificationController(SellerNotificationService sellerNotificationService) {
        this.sellerNotificationService = sellerNotificationService;
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody CartDataDto cartDataDto) throws JsonProcessingException {
        sellerNotificationService.sendMessage(cartDataDto);
    }
}
