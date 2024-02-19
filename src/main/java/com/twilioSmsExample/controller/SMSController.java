package com.twilioSmsExample.controller;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilioSmsExample.payload.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    @Value("${twilio.accountSid}")
    private String ACCOUNT_SID;

    @Value("${twilio.authToken}")
    private String AUTH_TOKEN;

    @Value("${twilio.phoneNumber}")
    private String FROM_PHONE_NUMBER;

    @PostMapping("/send-sms")
    public String sendSMS(@RequestBody SmsRequest smsRequest) {
       try
       {
           Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

           Message message = Message.creator(
                           new PhoneNumber(smsRequest.getTo()),
                           new PhoneNumber(FROM_PHONE_NUMBER),
                           smsRequest.getMessage())
                   .create();

           return "SMS sent successfully. SID: " + message.getSid();
       }
       catch (Exception e)
       {
           return String.valueOf(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed send sms"+e.getMessage()));
       }
    }
}
