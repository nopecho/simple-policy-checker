package com.nopecho.policy.adapters.out;

import com.nopecho.policy.adapters.out.persistence.PolicyRepository;
import com.nopecho.policy.applications.ports.out.PolicyLoadPort;
import com.nopecho.policy.applications.ports.out.PolicySavePort;
import com.nopecho.policy.domain.Policy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyPersistenceAdapter implements PolicyLoadPort, PolicySavePort {

    private final PolicyRepository repository;

    @Override
    public Long save(Policy policy) {
        Policy saved = repository.save(policy);
        return saved.getPolicyId();
    }

    @Override
    public Policy loadById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("정책을 찾을 수 없습니다."));
    }
}
