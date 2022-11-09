package io.jayaprabahar.camunda.ecommerce.common;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        AtomicReference<HttpStatus> httpStatusAtomicReference = new AtomicReference<>();

        responseSpec
                .toBodilessEntity()
                .subscribe(e -> httpStatusAtomicReference.set(e.getStatusCode()));

        return httpStatusAtomicReference.get() == HttpStatus.ACCEPTED;
    }
}
