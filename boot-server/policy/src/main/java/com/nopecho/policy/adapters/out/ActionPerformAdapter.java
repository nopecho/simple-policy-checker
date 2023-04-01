package com.nopecho.policy.adapters.out;

import com.nopecho.policy.adapters.out.handlers.VariableRequestHandlers;
import com.nopecho.policy.adapters.out.persistence.ActionHistoryRepository;
import com.nopecho.policy.applications.ports.out.ActionPerformPort;
import com.nopecho.policy.domain.*;
import com.nopecho.policy.domain.factors.Factor;
import com.nopecho.utils.JsonUtils;
import com.nopecho.utils.KoreaClocks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionPerformAdapter implements ActionPerformPort {

    private final VariableRequestHandlers handlers;
    private final ActionHistoryRepository repository;

    @Override
    public void perform(Action action, Factor factor, Set<String> variables) {
        RequestTemplate template = action.getTemplate();

        ActionHistory history = createHistory(action.getId(), template, factor, variables);
        try {
            handlers.handle(template, factor, variables);
            history.successFrom(KoreaClocks.now());
        } catch (Exception e) {
            log.error(e.getMessage());
            history.failFrom(KoreaClocks.now());
        } finally {
            repository.save(history);
        }
    }

    private ActionHistory createHistory(Long actionId, RequestTemplate template, Factor factor, Set<String> variables) {
        RequestTemplate historyTemplate = RequestTemplate.builder()
                .owner(template.getOwner())
                .requestType(template.getRequestType())
                .method(template.getMethod())
                .url(template.getUrl())
                .body(template.replaceVariableBody(factor, variables))
                .accessField(template.getAccessField())
                .build();
        return ActionHistory.of(actionId, JsonUtils.get().toJson(factor), historyTemplate);
    }
}
