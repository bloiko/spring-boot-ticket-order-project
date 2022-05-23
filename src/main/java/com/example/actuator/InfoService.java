package com.example.actuator;

import com.example.order.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class InfoService implements InfoContributor {
    @Autowired
    UserRepository userRepository;
    @Override
    public void contribute(Info.Builder builder) {
        HashMap<String, Integer> userCount = new HashMap<>();
        Integer count = userRepository.findAll().size();
        userCount.put("currentUsers", count);
        builder.withDetail("userMetrics", userCount);
    }
}
