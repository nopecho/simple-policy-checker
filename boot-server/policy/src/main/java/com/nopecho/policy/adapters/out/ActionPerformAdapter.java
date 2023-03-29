package com.nopecho.policy.adapters.out;

import com.nopecho.policy.adapters.out.handlers.VariableRequestHandlers;
import com.nopecho.policy.applications.ports.out.ActionPerformPort;
import com.nopecho.policy.domain.Action;
import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionPerformAdapter implements ActionPerformPort {

    private final VariableRequestHandlers handlers;

    @Override
    public void perform(Action action, Factor factor, Set<String> variables) {
        RequestTemplate template = action.getTemplate();

        handlers.handle(template, factor, variables);
    }
}
