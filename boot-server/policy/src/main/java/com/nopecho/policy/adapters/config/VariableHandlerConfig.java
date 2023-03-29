package com.nopecho.policy.adapters.config;

import com.nopecho.policy.adapters.out.handlers.DefaultActionHttpHandler;
import com.nopecho.policy.adapters.out.handlers.DefaultSpecHttpHandler;
import com.nopecho.policy.adapters.out.handlers.VariableRequestHandlers;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class VariableHandlerConfig {

    private final RestTemplate restTemplate;

    @Bean
    public VariableRequestHandlers variableRequestHandlers() {

        VariableRequestHandlers handlers = new VariableRequestHandlers();
        handlers.add(new DefaultActionHttpHandler(restTemplate));
        handlers.add(new DefaultSpecHttpHandler(restTemplate));

        return handlers;
    }
}
