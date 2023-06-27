package ar.com.sourcesistemas.helper.sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

import java.net.URISyntaxException;

public class Consumer {
    public void consume(String queueName) throws URISyntaxException {
        System.out.println("consuming");
        SqsClient sqsClient = SqsClientHelper.getSqsConnection();

        String queueUrl = String.format("http://localhost:9324/queue/%s",queueName);  // URL de la cola local

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
                .build();

        while (true) {
            var messages = sqsClient.receiveMessage(request).messages();

            for (Message message : messages) {
                System.out.println("Message received: " + message.body());

                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build();
                sqsClient.deleteMessage(deleteMessageRequest);
            }
        }
    }
}
