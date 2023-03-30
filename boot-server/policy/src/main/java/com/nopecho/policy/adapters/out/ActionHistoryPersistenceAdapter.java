package com.nopecho.policy.adapters.out;

import com.nopecho.policy.adapters.out.persistence.ActionHistoryRepository;
import com.nopecho.policy.domain.ActionHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionHistoryPersistenceAdapter {

    private final ActionHistoryRepository repository;

    public Long save(ActionHistory history) {
        ActionHistory saved = repository.save(history);
        return saved.getId();
    }
}
