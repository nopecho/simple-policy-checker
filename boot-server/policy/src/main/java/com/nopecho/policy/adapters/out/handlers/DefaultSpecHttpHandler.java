package com.nopecho.policy.adapters.out.handlers;

import com.nopecho.policy.domain.factors.Factor;
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
    public String handle(RequestTemplate template, Factor factor, Set<String> variables) {
        String method = template.getMethod().name();
        String url = template.getUrl();
        String body = template.replaceVariableBody(factor, variables);

        HttpMethod httpMethod = Objects.requireNonNull(HttpMethod.resolve(method));
        HttpEntity<String> httpBody = getHttpEntity(body);

        try {
            ResponseEntity<String> res = restTemplate.exchange(url, httpMethod, httpBody, String.class);
            if(res.getStatusCode().is2xxSuccessful()) {
                return JsonUtils.getOrThrowJsonValue(res.getBody(), template.getAccessField());
            }
            throw new IllegalStateException(requestFailMassage(method, url));
        } catch (Exception e) {
            throw new IllegalStateException(requestFailMassage(method, url));
        }
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

    private String requestFailMassage(String method, String url) {
        return String.format("[%s %s] Spec Variable Resolve 요청을 수행하지 못했습니다.", method, url);
    }
}
