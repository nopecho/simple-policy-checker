package com.nopecho.policy.adapters.out.handlers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractHttpHandler implements VariableRequestHandler {

    protected RestTemplate restTemplate;

    protected abstract HttpEntity<String> getHttpEntity(String body);
}
