package com.message.ring.services;

import com.message.ring.models.SmsStatus;
import com.message.ring.repository.SmsStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsStatusService {

    @Autowired
    private SmsStatusRepository smsStatusRepository;

    public void saveSmsStatus(SmsStatus smsStatus){
        smsStatusRepository.save(smsStatus);
    }

    public List<SmsStatus> getAllSmsLogs(){
        return smsStatusRepository.findAll();
    }

}
