package ar.com.sourcesistemas;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.net.URI;

public class SQSSender {
    public static void main(String[] args) {
        // Configura el cliente de SQS
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1) // Reemplaza con la región correcta
                .endpointOverride(URI.create("http://localhost:9324")) // Especifica la URL de ElasticMQ local
                .build();

        // Configura el mensaje
        String queueUrl = "http://localhost:9324/queue/my-queue"; // Reemplaza con la URL de tu cola en ElasticMQ local
        String messageBody = "Hello, ElasticMQ!";

        // Envía el mensaje
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();
        sqsClient.sendMessage(request);

        System.out.println("Message sent successfully!");
    }
}
