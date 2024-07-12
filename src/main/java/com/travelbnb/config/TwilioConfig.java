package com.travelbnb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;

@Configuration
public class TwilioConfig {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    @Bean
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
}

