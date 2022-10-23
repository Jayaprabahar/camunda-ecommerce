package io.jayaprabahar.camunda.ecommerce.emailservice.controller;

import io.jayaprabahar.camunda.ecommerce.common.EmailDto;
import io.jayaprabahar.camunda.ecommerce.emailservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    public EmailController(final EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public void sendMail(@RequestBody EmailDto emailDto) {
        log.info("Trigger Email for {}", emailDto);
        emailService.sendEmail(emailDto);
    }


}
