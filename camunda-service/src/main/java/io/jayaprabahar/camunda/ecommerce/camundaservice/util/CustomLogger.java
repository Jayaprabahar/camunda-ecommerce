package io.jayaprabahar.camunda.ecommerce.camundaservice.util;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@UtilityClass
@Slf4j
public class CustomLogger {

    public void logCamundaJob(final ActivatedJob job) {
        log.info(
                "complete job>>> type: {}, key: {}, element: {}, workflow instance: {}, deadline; {}, headers: {}][variable parameter: {}",
                job.getType(),
                job.getKey(),
                job.getElementId(),
                job.getProcessInstanceKey(),
                Instant.ofEpochMilli(job.getDeadline()),
                job.getCustomHeaders(),
                job.getVariables());
    }

    public void logAndThrowZeebeBpmnError(long processInstanceKey, String errorCode, String errorMessage){
        ZeebeBpmnError zeebeBpmnError = new ZeebeBpmnError(errorCode, errorMessage);
        log.error(String.format("ZeebeBpmnError for processInstanceKey %d", processInstanceKey), zeebeBpmnError);
        throw zeebeBpmnError;
    }
}
