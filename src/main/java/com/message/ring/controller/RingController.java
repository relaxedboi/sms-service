package com.message.ring.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.message.ring.models.SmsBody;
import com.message.ring.models.SmsBodyDTO;
import com.message.ring.models.SmsStatus;
import com.message.ring.services.AddMessageToTopic;
import com.message.ring.services.SmsStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RingController {

    @Autowired
    private AddMessageToTopic addMessageToTopic;

    @Autowired
    private SmsStatusService smsStatusService;

    @PostMapping("/sms/queue")
    public ResponseEntity<SmsBodyDTO> queueSms(@RequestBody SmsBody kafkaMessage) throws JsonProcessingException {
        addMessageToTopic.addMessageToQueue(kafkaMessage);
        SmsBodyDTO smsBodyDTO = SmsBodyDTO.builder()
                .remark("request added to the queue")
                .build();
        return ResponseEntity.ok(smsBodyDTO);
    }

    @GetMapping("/sms/logs")
    public ResponseEntity<List<SmsStatus>> getSmsLogs(){
        List<SmsStatus> smsStatusList = smsStatusService.getAllSmsLogs();
        return ResponseEntity.ok(smsStatusList);
    }
    
}
