package com.nopecho.policy.adapters.out.handlers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractSpecHttpHandler extends AbstractHttpHandler {
    // Spec 공통 필드

    protected AbstractSpecHttpHandler(RestTemplate restTemplate) {
        super(restTemplate);
    }
}
