package io.jayaprabahar.camunda.ecommerce.camundaservice.util;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@UtilityClass
@Slf4j
public class JobLogger {

    public void logJob(final ActivatedJob job) {
        log.info(
                "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variable parameter: {}",
                job.getType(),
                job.getKey(),
                job.getElementId(),
                job.getProcessInstanceKey(),
                Instant.ofEpochMilli(job.getDeadline()),
                job.getCustomHeaders(),
                job.getVariables());
    }
}
