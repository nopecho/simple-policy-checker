package com.nopecho.policy.action.adapters.out;

import com.nopecho.policy.action.adapters.out.persistence.ActionHistoryRepository;
import com.nopecho.policy.action.applications.ports.out.ActionHistoryLoadPort;
import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.HistoryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistoryPersistenceAdapter implements ActionHistoryLoadPort {

    private final ActionHistoryRepository repository;

    @Override
    public List<ActionHistory> loadByStatus(HistoryStatus status) {
        return repository.findByStatus(status);
    }
}
