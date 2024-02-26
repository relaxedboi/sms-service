package com.message.ring.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsBody implements Serializable {

    private String message;
    private String senderNumber;
    private String receiverNumber;

}
