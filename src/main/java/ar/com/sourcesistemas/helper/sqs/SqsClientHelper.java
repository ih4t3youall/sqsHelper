package ar.com.sourcesistemas.helper.sqs;

import ar.com.sourcesistemas.exception.NotImplementedException;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.net.URI;
import java.net.URISyntaxException;

public class SqsClientHelper {

    private static SqsClient sqsClient;

    static {
        try {
            sqsClient = getSqsConnection();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqsClient getSqsConnection() throws URISyntaxException {
        return SqsClient.builder()
                .endpointOverride(new URI("http://localhost:9324"))
                .region(Region.AP_SOUTH_1)  // URL local de ElasticMQ
                .credentialsProvider(new AwsCredentialsProvider() {
                    @Override
                    public AwsCredentials resolveCredentials() {
                        return new AwsCredentials() {
                            @Override
                            public String accessKeyId() {
                                return "1string";
                            }

                            @Override
                            public String secretAccessKey() {
                                return "2string";
                            }
                        };
                    }
                })
                .build();
    }

    public static String createQueue(String queueName){
        CreateQueueRequest request1 = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();
        CreateQueueResponse response = sqsClient.createQueue(request1);
        String queueUrl = response.queueUrl();
        System.out.println(String.format("Queue %s created.",queueUrl));
        return queueUrl;
    }

    public static void sendMessage(String message, String url) throws NotImplementedException {
        //TODO?
        throw new NotImplementedException();
        //SendMessageRequest request = SendMessageRequest.builder()
        //        .queueUrl(queueUrl)
        //        .messageBody(message)
        //        .build();

        //sqsClient.sendMessage(request);
    }

    public static void receiveMessage(String queueName) throws NotImplementedException {
        //TODO ?
        throw new NotImplementedException();
        //SqsClient sqsClient = SqsClientHelper.getSqsConnection();

        //String queueUrl = String.format("http://localhost:9324/queue/%s",queueName);  // URL de la cola local

        //ReceiveMessageRequest request = ReceiveMessageRequest.builder()
        //        .queueUrl(queueUrl)
        //        .maxNumberOfMessages(1)
        //        .build();

        //while (true) {
        //    var messages = sqsClient.receiveMessage(request).messages();

        //    for (Message message : messages) {
        //        System.out.println("Message received: " + message.body());

        //        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
        //                .queueUrl(queueUrl)
        //                .receiptHandle(message.receiptHandle())
        //                .build();
        //        sqsClient.deleteMessage(deleteMessageRequest);
        //    }
        //}

    }

    public static Boolean queueExists(String queueName){
        ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().queueNamePrefix(queueName).build();
        ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);

        if (listQueuesResponse.queueUrls().size() > 0) {
            System.out.println("La cola exist");
            return true;
        } else {
            System.out.println("La cola no exist");
            return false;
        }
    }
}
