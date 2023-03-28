package com.nopecho.policy.applications.ports.out;

import com.nopecho.policy.domain.Policy;

public interface PolicySavePort {
    Long save(Policy policy);
}
