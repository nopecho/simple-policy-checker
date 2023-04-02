package com.nopecho.policy.action.applications.ports.out;

import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.HistoryStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ActionHistoryLoadPort {

    List<ActionHistory> loadByStatus(HistoryStatus status);

    Slice<ActionHistory> loadAllByPageable(Pageable pageable);

    ActionHistory loadById(Long id);
}
