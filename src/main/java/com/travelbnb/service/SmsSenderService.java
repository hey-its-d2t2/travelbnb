package com.travelbnb.service;
import com.travelbnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderService {

   /* @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;*/

    private final TwilioConfig twilioConfig;

    @Autowired
    public SmsSenderService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public String sendSms(String toPhoneNumber, String message) {
       /* Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();*/
        //or
        Message sms = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioConfig.getPhoneNumber()),message
        ).create();
        return sms.getSid();
    }
}