package com.example.demo1.service;

import com.example.demo1.config.TwilioConfiguration;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private TwilioConfiguration twilioConfiguration;

    private Map<String, String> otpMap = new HashMap<>();
    public void sendOtp(String phoneNumber) {
        String otp = generatedOtp();
        otpMap.put(phoneNumber,otp);

        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());

        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(twilioConfiguration.getPhoneNumber()),
                "Your OTP is:"+ otp)
                .create();
        System.out.println("OTP sent to " + phoneNumber);
    }
    public boolean verifyOtp(String phoneNumber,String otp){
        if (otpMap.containsKey(phoneNumber)) {
            String storedOtp = otpMap.get(phoneNumber);
            return storedOtp.equals(otp);
        }
        return false;
    }
    private String generatedOtp() {
        return String.format("%06d", new Random().nextInt(999999));

    }
}

