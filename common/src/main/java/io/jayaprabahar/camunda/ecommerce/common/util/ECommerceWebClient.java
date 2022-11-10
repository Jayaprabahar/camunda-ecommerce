package io.jayaprabahar.camunda.ecommerce.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Objects;

@UtilityClass
@Slf4j
public class ECommerceWebClient {

    public HttpStatus isPostResponseAccepted(String url, Object body) {
        return getResponseCode(WebClient.create().post().uri(url).body(BodyInserters.fromValue(body)).retrieve());
    }

    public HttpStatus isPatchResponseAccepted(String url) {
        return getResponseCode(WebClient.create().patch().uri(url).retrieve());
    }

    private HttpStatus getResponseCode(WebClient.ResponseSpec responseSpec) {
        return Objects.requireNonNull(responseSpec
                .toBodilessEntity()
                .block(Duration.ofSeconds(30))).getStatusCode();
    }
}
