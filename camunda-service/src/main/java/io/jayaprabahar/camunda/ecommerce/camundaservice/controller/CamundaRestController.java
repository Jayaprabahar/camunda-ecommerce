package io.jayaprabahar.camunda.ecommerce.camundaservice.controller;

import io.jayaprabahar.camunda.ecommerce.camundaservice.service.CamundaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CamundaRestController {

    private final CamundaService camundaService;

    @Autowired
    public CamundaRestController(final CamundaService camundaService) {
        this.camundaService = camundaService;
    }

    @PostMapping("/checkOut")
    public ResponseEntity<String> checkOut(@RequestBody MultiValueMap<String, String> cartData) {
        log.info("Starting process with {}", cartData);
        String orderId = UUID.randomUUID().toString();
        cartData.set("orderId", orderId);
        camundaService.initiateOrderFlow(cartData);

        return new ResponseEntity<>("Order created with id "+orderId, HttpStatus.ACCEPTED);
    }

}
