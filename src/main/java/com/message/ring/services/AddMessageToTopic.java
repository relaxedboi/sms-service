package com.message.ring.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.ring.models.SmsBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AddMessageToTopic {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void addMessageToQueue(SmsBody message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(message);
        kafkaTemplate.send("sms",json);
    }

}
