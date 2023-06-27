package ar.com.sourcesistemas;

import ar.com.sourcesistemas.helper.sqs.Consumer;
import ar.com.sourcesistemas.helper.sqs.Producer;
import ar.com.sourcesistemas.helper.sqs.SqsClientHelper;


public class Main {
    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws Exception {
        String queueName = "queue1";
        if  (!SqsClientHelper.queueExists(queueName)){
            SqsClientHelper.createQueue(queueName);
        }
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        producer.produce("a message", queueName);
        consumer.consume(queueName);


    }
}