package com.nopecho.policy.adapters.out;


import com.nopecho.policy.adapters.out.external.VariableRequestHandlers;
import com.nopecho.policy.applications.ports.out.SpecVariablePort;
import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.RequestTemplate;
import com.nopecho.policy.domain.Spec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpecVariableAdapter implements SpecVariablePort {

    private final VariableRequestHandlers handlers;

    @Override
    public void resolve(Spec spec, Factor factor, Set<String> variables) {
        RequestTemplate template = spec.getActual().getTemplate();

        String result = handlers.handle(template, factor, variables);

        spec.getActual().setResult(result);
    }
}
