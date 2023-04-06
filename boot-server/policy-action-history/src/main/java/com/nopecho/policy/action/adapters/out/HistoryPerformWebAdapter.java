package com.nopecho.policy.action.adapters.out;

import com.nopecho.policy.action.adapters.out.persistence.ActionHistoryRepository;
import com.nopecho.policy.action.applications.ports.out.ActionHistoryPerformPort;
import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.RequestTemplate;
import com.nopecho.utils.KoreaClocks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistoryPerformWebAdapter implements ActionHistoryPerformPort {

    private final ActionHistoryRepository repository;
    private final RestTemplate restTemplate;

    @Override
    public boolean perform(ActionHistory history) {
        RequestTemplate template = history.getTemplate();
        String method = template.getMethod().name();
        String url = template.getUrl();
        String body = template.getBody();

        HttpMethod httpMethod = Objects.requireNonNull(HttpMethod.resolve(method));
        HttpEntity<String> httpEntity = getHttpEntity(body);

        try {
            ResponseEntity<String> res = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            if(res.getStatusCode().is2xxSuccessful()) {
                history.successFrom(KoreaClocks.now());
                return true;
            }
            history.failFrom(KoreaClocks.now());
            return false;
        } catch (Exception e) {
            history.failFrom(KoreaClocks.now());
            return false;
        } finally {
            repository.save(history);
        }
    }

    private HttpEntity<String> getHttpEntity(String body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
