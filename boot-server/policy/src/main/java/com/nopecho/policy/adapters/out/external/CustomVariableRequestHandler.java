package com.nopecho.policy.adapters.out.external;

import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.RequestMethod;
import com.nopecho.policy.domain.RequestTemplate;
import com.nopecho.policy.domain.RequestType;
import com.nopecho.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Set;


@RequiredArgsConstructor
public class CustomVariableRequestHandler implements VariableRequestHandler{

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Override
    public String requestFor(RequestTemplate template, Factor factor, Set<String> variables) {
        String method = template.getMethod().name();
        String url = template.getUrl();
        String body = variables.stream()
                .map(variable -> template.replaceVariableBody(factor, variable))
                .toString();

        HttpMethod httpMethod = Objects.requireNonNull(HttpMethod.resolve(method));
        HttpEntity<String> httpBody = new HttpEntity<>(body, this.headers);

        ResponseEntity<String> res = restTemplate.exchange(url, httpMethod, httpBody, String.class);

        return JsonUtils.getOrThrowJsonValue(res.getBody(), template.getAccessField());
    }

    @Override
    public boolean isSupport(RequestTemplate template) {
        return template.getRequestType().equals(RequestType.HTTP);
    }
}
