package io.jayaprabahar.camunda.ecommerce.common;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
@Slf4j
public class ECommerceWebClient {

    public boolean isPostResponseAccepted(String url, Object body) {
        return getResponseCode(WebClient.create().post().uri(url).body(BodyInserters.fromValue(body)).retrieve());
    }

    public boolean isPatchResponseAccepted(String url) {
        return getResponseCode(WebClient.create().patch().uri(url).retrieve());
    }

    private boolean getResponseCode(WebClient.ResponseSpec responseSpec) {
        AtomicReference<ResponseEntity<Void>> httpStatusAtomicReference = new AtomicReference<>();

        responseSpec
                .toBodilessEntity()
                .subscribe(
                        httpStatusAtomicReference::set,
                        errorConsumer -> log.error("Error making network call %s", errorConsumer)
                );

        return httpStatusAtomicReference.get() != null && httpStatusAtomicReference.get().getStatusCode() == HttpStatus.ACCEPTED;
    }
}
