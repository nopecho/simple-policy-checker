package com.nopecho.policy.applications.ports.out;

import com.nopecho.policy.domain.Action;
import com.nopecho.policy.domain.factors.Factor;

import java.util.Set;

public interface ActionPerformPort {
    void perform(Action action, Factor factor, Set<String> variables);
}
