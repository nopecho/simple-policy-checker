package com.nopecho.policy.adapters.out.external;

import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.RequestTemplate;

import java.util.Set;

public interface VariableRequestHandler {
    String requestFor(RequestTemplate template, Factor factor, Set<String> variables);
    boolean isSupport(RequestTemplate template);
}
