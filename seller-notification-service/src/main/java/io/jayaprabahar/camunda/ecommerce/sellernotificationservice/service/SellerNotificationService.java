package io.jayaprabahar.camunda.ecommerce.sellernotificationservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jayaprabahar.camunda.ecommerce.common.dto.CartDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
public class SellerNotificationService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket}")
    private String s3BucketName;

    public void sendMessage(CartDataDto cartDataDto) throws IOException {
        final File file = new File(cartDataDto.getOrderId()[0] + "-"+ System.currentTimeMillis()+".json") ;

        try(final FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(OBJECT_MAPPER.writeValueAsString(cartDataDto));
            fileWriter.close();
            log.info("Uploaded file with name {}", file.getName());

            final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, file.getName(), file);
            amazonS3.putObject(putObjectRequest);
            Files.delete(file.toPath());
        }
    }
}
