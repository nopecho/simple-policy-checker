package com.nopecho.policy.action.applications.ports.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface NotificationPublishPort {

    void publish(Payload payload);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Payload {
        String apiType;
        String to;
        String title;
        String message;
    }
}
