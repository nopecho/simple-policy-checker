package com.nopecho.policy.adapters.out.handlers;

import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.RequestOwner;
import com.nopecho.policy.domain.RequestTemplate;
import com.nopecho.policy.domain.RequestType;
import com.nopecho.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Set;

@Slf4j
public class DefaultSpecHttpHandler extends AbstractSpecHttpHandler{

    public DefaultSpecHttpHandler(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public String requestFor(RequestTemplate template, Factor factor, Set<String> variables) {
        String method = template.getMethod().name();
        String url = template.getUrl();
        String body = template.replaceVariableBody(factor, variables);

        HttpMethod httpMethod = Objects.requireNonNull(HttpMethod.resolve(method));
        HttpEntity<String> httpBody = getHttpEntity(body);

        ResponseEntity<String> res = restTemplate.exchange(url, httpMethod, httpBody, String.class);

        return JsonUtils.getOrThrowJsonValue(res.getBody(), template.getAccessField());
    }

    @Override
    public boolean isSupport(RequestTemplate template) {
        return template.getRequestType().equals(RequestType.HTTP)
                && template.getOwner().equals(RequestOwner.SPEC);
    }

    @Override
    protected HttpEntity<String> getHttpEntity(String body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
