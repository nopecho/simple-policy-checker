package com.nopecho.policy.adapters.out.handlers;

import com.nopecho.policy.domain.factors.Factor;
import com.nopecho.policy.domain.RequestTemplate;

import java.util.Set;

public interface VariableRequestHandler {
    String handle(RequestTemplate template, Factor factor, Set<String> variables);
    boolean isSupport(RequestTemplate template);
}
