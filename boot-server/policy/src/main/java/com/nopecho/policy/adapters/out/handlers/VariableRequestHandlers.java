package com.nopecho.policy.adapters.out.handlers;

import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.RequestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class VariableRequestHandlers {

    private final List<VariableRequestHandler> containers = new ArrayList<>();

    public void add(VariableRequestHandler handler) {
        this.containers.add(handler);
    }

    public String handle(RequestTemplate template, Factor factor, Set<String> variables) {
        VariableRequestHandler handler = this.containers.stream()
                .filter(h -> h.isSupport(template))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(template.toString() + " ::지원하지 않는 요청 입니다."));

        return handler.handle(template, factor, variables);
    }
}
