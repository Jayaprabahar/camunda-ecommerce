package io.jayaprabahar.camunda.ecommerce.sellernotificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jayaprabahar.camunda.ecommerce.common.dto.CartDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Slf4j
public class SellerNotificationService {
    private static final SqsClient SQS_CLIENT = SqsClient.builder().region(Region.EU_WEST_3).build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final SendMessageRequest.Builder SQS_MSG_BUILDER = SendMessageRequest.builder().queueUrl("https://sqs.eu-west-3.amazonaws.com/395189059856/camunda-ecommerce-order-queue");

    public int sendMessage(CartDataDto cartDataDto) throws JsonProcessingException {
        SendMessageRequest messageRequest = SQS_MSG_BUILDER.messageBody(OBJECT_MAPPER.writeValueAsString(cartDataDto)).build();
        SendMessageResponse sendMessageResponse = SQS_CLIENT.sendMessage(messageRequest);

        log.info("sendMessageResponse {}", sendMessageResponse);
        return sendMessageResponse.sdkHttpResponse().statusCode();
    }
}
