package com.zirconlabz.ordermanager.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaResource {

    private KafkaTemplate<String, String> template;
    private ConsumerFactory<String, String> consumerFactory;
    private static final String TOPIC = "zircon-test";

    public KafkaResource(KafkaTemplate<String, String> template, ConsumerFactory<String, String> consumerFactory){
        this.template = template;
        this.consumerFactory = consumerFactory;
        template.setConsumerFactory(consumerFactory);
    }

    @GetMapping("/send")
    public void send(@RequestParam("msg") String msg) {
        System.out.println("Sending message: "+msg);
        ListenableFuture<SendResult<String, String>> future = template.send("zircon-test", msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + msg +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + msg + "] due to : " + ex.getMessage());
            }
        });
    }

    @GetMapping("")
    public String getMessages(@RequestParam("offset") int offset){
        System.out.println("Getting msgs::");
        StringBuilder out = new StringBuilder();
        ConsumerRecord<String, String> record = template.receive(TOPIC, 0, offset);
        for (int i = 0; i < 100; i++) {
            ConsumerRecord<String, String> _record = template.receive(TOPIC, 0, i);
            if (_record.value() == null) {
                break;
            }
            String value = _record.value();
            out.append(value);
            out.append("\n");
        }
        return "MSG: "+out;
    }
}
