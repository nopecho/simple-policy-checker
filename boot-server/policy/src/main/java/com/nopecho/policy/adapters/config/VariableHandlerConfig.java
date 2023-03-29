package com.nopecho.policy.adapters.config;

import com.nopecho.policy.adapters.out.external.CustomVariableRequestHandler;
import com.nopecho.policy.adapters.out.external.VariableRequestHandlers;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class VariableHandlerConfig {

    private final RestTemplate restTemplate;

    @Bean
    public VariableRequestHandlers variableRequestHandlers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        VariableRequestHandlers handlers = new VariableRequestHandlers();
        handlers.add(new CustomVariableRequestHandler(restTemplate, headers));

        return handlers;
    }
}
