package ar.com.sourcesistemas.helper.sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.net.URISyntaxException;

public class Producer {
    public void produce(String message, String queueName) throws URISyntaxException {
        System.out.println("Producing");

        SqsClient sqsClient = SqsClientHelper.getSqsConnection();

        String queueUrl = SqsClientHelper.createQueue(queueName);

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();

        sqsClient.sendMessage(request);

        System.out.println("Mesaje sent.");
    }
}
