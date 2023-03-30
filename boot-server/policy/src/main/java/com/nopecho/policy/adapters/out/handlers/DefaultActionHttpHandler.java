package com.nopecho.policy.adapters.out.handlers;

import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.RequestOwner;
import com.nopecho.policy.domain.RequestTemplate;
import com.nopecho.policy.domain.RequestType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Set;

@Slf4j
public class DefaultActionHttpHandler extends AbstractActionHttpHandler {

    public DefaultActionHttpHandler(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public String handle(RequestTemplate template, Factor factor, Set<String> variables) {
        String method = template.getMethod().name();
        String url = template.getUrl();
        String body = template.replaceVariableBody(factor, variables);

        HttpMethod httpMethod = Objects.requireNonNull(HttpMethod.resolve(method));
        HttpEntity<String> httpEntity = getHttpEntity(body);

        try {
            ResponseEntity<String> res = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            return res.getBody();
        } catch (Exception e) {
            String message = String.format("[%s %s] Action 요청을 수행하지 못했습니다.", httpMethod.name(), url);
            throw new IllegalStateException(message);
        }
    }

    @Override
    public boolean isSupport(RequestTemplate template) {
        return template.getRequestType().equals(RequestType.HTTP)
                && template.getOwner().equals(RequestOwner.ACTION);
    }

    @Override
    protected HttpEntity<String> getHttpEntity(String body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
