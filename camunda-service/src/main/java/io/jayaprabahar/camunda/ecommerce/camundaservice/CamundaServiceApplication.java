package io.jayaprabahar.camunda.ecommerce.camundaservice;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = {"classpath*:/bpmn/**/*.bpmn", "classpath*:/form/**/*.form"})
public class CamundaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaServiceApplication.class, args);
	}

}
