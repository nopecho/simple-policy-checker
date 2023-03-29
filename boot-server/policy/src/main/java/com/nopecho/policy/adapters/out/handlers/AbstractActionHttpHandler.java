package com.nopecho.policy.adapters.out.handlers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AbstractActionHttpHandler extends AbstractHttpHandler {
    // Action 공통 필드

    protected AbstractActionHttpHandler(RestTemplate restTemplate) {
        super(restTemplate);
    }
}
