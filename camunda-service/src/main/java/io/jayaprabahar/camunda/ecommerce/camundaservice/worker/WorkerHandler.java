package io.jayaprabahar.camunda.ecommerce.camundaservice.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import io.jayaprabahar.camunda.ecommerce.camundaservice.util.JobLogger;
import io.jayaprabahar.camunda.ecommerce.common.EmailDto;
import io.jayaprabahar.camunda.ecommerce.common.EmailType;
import io.jayaprabahar.camunda.ecommerce.common.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class WorkerHandler {

    @JobWorker
    public void confirmOrder(final ActivatedJob job) {
        JobLogger.logJob(job);

        Map<String, Object> cartData = job.getVariablesAsMap();
        String productName = (String) ((List<?>) cartData.get("product")).get(0);

        if (!isInventoryAvailable(productName)) {
            throw new ZeebeBpmnError("NO_STOCK", "Unfortunately all the stocks are sold out");
        }
    }

    @JobWorker
    public void emailNoStock(final ActivatedJob job) {
        JobLogger.logJob(job);

        Map<String, Object> cartData = job.getVariablesAsMap();
        String orderId = (String) ((List<?>) cartData.get("orderId")).get(0);
        String emailAddress = (String) ((List<?>) cartData.get("email")).get(0);

        if (!emailNoStockError(new EmailDto(orderId, emailAddress, EmailType.NO_STOCK))) {
            throw new ZeebeBpmnError("EMAIL_SERVER_DOWN", "EMAIL_SERVER_DOWN");
        }
    }


    public boolean isInventoryAvailable(String itemName) {
        AtomicReference<HttpStatus> httpStatusResults = new AtomicReference<>();
        Mono<HttpStatus> httpStatus = WebClient.create().post()
                .uri(ServiceDiscovery.INVENTORY_SERVICE.getUrl() + itemName)
                .retrieve()
                .toBodilessEntity()
                .map(ResponseEntity::getStatusCode)
                .onErrorComplete((throwable, object) ->
                        log.error("Server {} down ", ServiceDiscovery.INVENTORY_SERVICE.getUrl()));

        httpStatus.subscribe(result -> httpStatusResults.set(result),
                error -> httpStatusResults.set(HttpStatus.NOT_FOUND)).dispose();

        return httpStatusResults.get() == HttpStatus.ACCEPTED;
    }

    public boolean emailNoStockError(EmailDto emailDto) {
        AtomicReference<HttpStatus> httpStatusResults = new AtomicReference<>();
        Mono<HttpStatus> httpStatus = WebClient.create().post()
                .uri(ServiceDiscovery.EMAIL_SERVICE.getUrl())
                .body(BodyInserters.fromValue(emailDto))
                .retrieve()
                .toBodilessEntity()
                .map(ResponseEntity::getStatusCode)
                .onErrorContinue((throwable, object) ->
                        log.error("Server {} down ", ServiceDiscovery.INVENTORY_SERVICE.getUrl()));

        httpStatus.subscribe(result -> httpStatusResults.set(result),
                error -> httpStatusResults.set(HttpStatus.NOT_FOUND)).dispose();

        return httpStatusResults.get() == HttpStatus.ACCEPTED;
    }

}
