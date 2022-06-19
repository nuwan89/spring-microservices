package com.zirconlabz.ordermanager.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = "zircon-test", groupId = "x")
    public void listenGroupFoo(String message) {
        System.out.println("======== Received Message in group foo: " + message);
    }

    @KafkaListener(groupId = "y", topicPartitions = @TopicPartition(
            topic = "zircon-test", partitionOffsets = { @PartitionOffset(
                    partition = "0",
                    initialOffset = "0"
    )}))
    public void fromBeginning(@Payload String message, @Header(KafkaHeaders.OFFSET) int offset){
        System.out.println("*********************************: ["+offset+"] "+message+" partition: ");
    }
}
