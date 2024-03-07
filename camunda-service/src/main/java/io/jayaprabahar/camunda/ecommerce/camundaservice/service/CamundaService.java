package io.jayaprabahar.camunda.ecommerce.camundaservice.service;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class CamundaService {

    private final ZeebeClient zeebeClient;

    @Autowired
    public CamundaService(final ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public void initiateOrderFlow(MultiValueMap<String, String> cartData) {
        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("order-delivery-enhanced")
                .latestVersion()
                .variables(cartData)
                .send().join();
    }

}
