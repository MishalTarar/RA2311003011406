package com.test.scheduler.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String ACCESS_TOKEN = "your_token";

    public void sendLog(String stack, String level, String message) {

        String url = "http://20.207.122.201/evaluation-service/logs";

        if (message.length() > 48) {
            message = message.substring(0, 48);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + ACCESS_TOKEN);

        Map<String, String> body = new HashMap<>();
        body.put("stack", stack);
        body.put("level", level);
        body.put("package", "controller");
        body.put("message", message);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            System.out.println("Log sent: " + response.getStatusCode());

        } catch (Exception e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}