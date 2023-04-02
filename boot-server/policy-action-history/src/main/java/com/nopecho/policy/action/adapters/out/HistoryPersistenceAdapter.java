package com.nopecho.policy.action.adapters.out;

import com.nopecho.policy.action.adapters.out.persistence.ActionHistoryRepository;
import com.nopecho.policy.action.applications.ports.out.ActionHistoryLoadPort;
import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.HistoryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @Override
    public Slice<ActionHistory> loadAllByPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ActionHistory loadById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("[ID: %s]ActionHistory 가 존재하지 않습니다.", id)));
    }
}
