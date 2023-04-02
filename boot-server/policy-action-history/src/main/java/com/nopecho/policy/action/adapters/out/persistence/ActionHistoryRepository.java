package com.nopecho.policy.action.adapters.out.persistence;

import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.HistoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Long> {

    List<ActionHistory> findByStatus(HistoryStatus status);

    Page<ActionHistory> findAll(Pageable pageable);

    Optional<ActionHistory> findByIdAndStatus(Long id, HistoryStatus status);
}
