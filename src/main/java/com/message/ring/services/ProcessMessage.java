package com.message.ring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.ring.models.SmsBody;
import com.message.ring.models.SmsStatus;
import com.message.ring.models.Status;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProcessMessage {

    @Autowired
    private SmsStatusService smsStatusService;

    @KafkaListener(topics = "sms", groupId = "my-group", autoStartup = "true",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        ObjectMapper objectMapper = new ObjectMapper();
        SmsBody kafkaMessage = objectMapper.convertValue(record.value(), SmsBody.class);
        // Process the message
        if(sendSms(kafkaMessage.getReceiverNumber(), kafkaMessage.getMessage())) {
            //If the sms was sent successfully then commit the offset,
            // So that if sms is sent then next message should be picked from queue.
            acknowledgment.acknowledge();
            SmsStatus smsStatus = SmsStatus
                    .builder()
                    .status(Status.SUCCESS)
                    .sendNumber(kafkaMessage.getSenderNumber())
                    .receiverNumber(kafkaMessage.getReceiverNumber())
                    .date(Instant.now())
                    .build();

            smsStatusService.saveSmsStatus(smsStatus);
        }else {
            SmsStatus smsStatus = SmsStatus
                    .builder()
                    .status(Status.FAILURE)
                    .sendNumber(kafkaMessage.getSenderNumber())
                    .receiverNumber(kafkaMessage.getReceiverNumber())
                    .date(Instant.now())
                    .build();

            smsStatusService.saveSmsStatus(smsStatus);
        }
    }

    //Making a third party API call to send sms
    public static boolean sendSms(String phoneNumber, String message) {
      //sending sms from this method
        return true;
    }
}
