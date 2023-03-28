package com.nopecho.policy.applications.ports.out;

import com.nopecho.policy.domain.Action;

public interface ActionPerformPort {
    void perform(Action action);
}
