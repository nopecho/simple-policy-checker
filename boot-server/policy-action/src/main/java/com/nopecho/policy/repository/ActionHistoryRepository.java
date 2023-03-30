package com.nopecho.policy.repository;

import com.nopecho.policy.domain.ActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Long> {
}
