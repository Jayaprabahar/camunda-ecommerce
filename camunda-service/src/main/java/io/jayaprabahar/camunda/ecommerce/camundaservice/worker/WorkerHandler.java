package io.jayaprabahar.camunda.ecommerce.camundaservice.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.jayaprabahar.camunda.ecommerce.camundaservice.util.CustomLogger;
import io.jayaprabahar.camunda.ecommerce.common.EmailType;
import io.jayaprabahar.camunda.ecommerce.common.ServiceDiscovery;
import io.jayaprabahar.camunda.ecommerce.common.dto.CartDataDto;
import io.jayaprabahar.camunda.ecommerce.common.dto.EmailDto;
import io.jayaprabahar.camunda.ecommerce.common.util.ECommerceWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@SuppressWarnings("unused")
public class WorkerHandler {

    @Value("seller.mail-address")
    String sellerMailAddress;

    @JobWorker
    public void confirmOrder(final ActivatedJob job) {
        CustomLogger.logCamundaJob(job);

        String productName = job.getVariablesAsType(CartDataDto.class).getProduct()[0];

        if (ECommerceWebClient.isPatchResponseAccepted(ServiceDiscovery.INVENTORY_SERVICE.getUrl() + productName) != HttpStatus.ACCEPTED) {
            CustomLogger.logAndThrowZeebeBpmnError(job.getKey(), "NO_STOCK", "Unfortunately all the stocks are sold out");
        }
    }

    @JobWorker
    public void emailNoStock(final ActivatedJob job) {
        CustomLogger.logCamundaJob(job);

        String orderId = job.getVariablesAsType(CartDataDto.class).getOrderId()[0];
        String emailAddress = job.getVariablesAsType(CartDataDto.class).getEmail()[0];

        if (ECommerceWebClient.isPostResponseAccepted(ServiceDiscovery.EMAIL_SERVICE.getUrl(), new EmailDto(orderId, emailAddress, EmailType.NO_STOCK)) != HttpStatus.OK) {
            CustomLogger.logAndThrowZeebeBpmnError(job.getKey(), "EMAIL_SERVER_DOWN", "EMAIL_SERVER_DOWN");
        }
    }

    @JobWorker
    public void informSeller(final ActivatedJob job) {
        CustomLogger.logCamundaJob(job);

        // Place the order file in Seller's AWS SQS queue
        if (ECommerceWebClient.isPostResponseAccepted(ServiceDiscovery.SELLER_NOTIFICATION_SERVICE.getUrl(),
                job.getVariablesAsType(CartDataDto.class)) != HttpStatus.OK) {
            CustomLogger.logAndThrowZeebeBpmnError(job.getKey(), "SELLER_NOTIFICATION_FAILED", "SELLER_NOTIFICATION_FAILED");
        }
    }

    @JobWorker
    public void emailServerDownError(final ActivatedJob job) {
        CustomLogger.logCamundaJob(job);

        String orderId = job.getVariablesAsType(CartDataDto.class).getOrderId()[0];

        if (ECommerceWebClient.isPostResponseAccepted(ServiceDiscovery.EMAIL_SERVICE.getUrl(),
                new EmailDto(orderId, sellerMailAddress, EmailType.EMAIL_SERVER_DOWN)) != HttpStatus.OK) {
            CustomLogger.logAndThrowZeebeBpmnError(job.getKey(), "EMAIL_SERVER_DOWN", "EMAIL_SERVER_DOWN");
        }
    }

}
