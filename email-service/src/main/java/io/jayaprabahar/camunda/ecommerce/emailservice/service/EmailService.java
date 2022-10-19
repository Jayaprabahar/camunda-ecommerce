package io.jayaprabahar.camunda.ecommerce.emailservice.service;

import io.jayaprabahar.camunda.ecommerce.common.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {


    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String fromAddress;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(EmailDto emailDto) {
        String subject = String.format(switch (emailDto.getType()) {
            case ORDER_CONFIRM -> "Order id %s is Confirmed";
            case SHIPMENT_DETAIL -> "Order id %s is Shipped";
            case CUSTOMER_RECEIVED_ITEM -> "Customer received Order id %s";
            case PRODUCT_RETURNED -> "Customer returned Order id %s";
            case PRODUCT_RETURNED_DEFECTIVE -> "Item returned by customer as defective for Order id %s";
            case NO_STOCK -> "Sorry! The item in your Order id %s soldout";
        }, emailDto.getOrderId());

        sendEmail(emailDto.getEmailAddress(), subject);
    }

    public void sendEmail(String emailAddress, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject(content);
        mailMessage.setText(content);

        // Sending the mail
        javaMailSender.send(mailMessage);
    }

}
