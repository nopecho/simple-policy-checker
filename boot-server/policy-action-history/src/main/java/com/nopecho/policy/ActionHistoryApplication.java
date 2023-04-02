package com.nopecho.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class ActionHistoryApplication {

    @RestController
    public static class HealthController {
        @GetMapping(value = "/ping")
        public String hello() {
            return "pong";
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(ActionHistoryApplication.class, args);
    }
}
