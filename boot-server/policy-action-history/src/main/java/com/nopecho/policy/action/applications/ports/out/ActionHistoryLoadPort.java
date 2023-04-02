package com.nopecho.policy.action.applications.ports.out;

import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.HistoryStatus;

import java.util.List;

public interface ActionHistoryLoadPort {

    List<ActionHistory> loadByStatus(HistoryStatus status);
}
