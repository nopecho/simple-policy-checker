package com.nopecho.policy.action.adapters.out;

import com.nopecho.policy.action.applications.ports.out.NotificationPublishPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPublishPort {

    private final RestTemplate restTemplate;

    @Override
    public void publish(Payload payload) {
        log.info(payload.getMessage());
    }
}
