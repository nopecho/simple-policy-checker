package com.nopecho.policy.adapters.out.persistence;

import com.nopecho.policy.domain.ActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Long> {
}
