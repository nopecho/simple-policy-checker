package com.nopecho.policy.action.applications.ports.out;

import com.nopecho.policy.domain.ActionHistory;

public interface ActionHistoryPerformPort {

    boolean perform(ActionHistory history);
}
