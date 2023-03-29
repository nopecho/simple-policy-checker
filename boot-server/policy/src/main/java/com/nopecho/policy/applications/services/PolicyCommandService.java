package com.nopecho.policy.applications.services;

import com.nopecho.policy.applications.ports.out.PolicySavePort;
import com.nopecho.policy.applications.usecases.PolicyCreateUseCase;
import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.domain.Policy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyCommandService implements PolicyCreateUseCase {

    private final PolicySavePort savePort;

    @Override
    public Long create(Request.PolicyModel policyModel) {
        Policy policy = PolicyFactory.create(policyModel);

        return savePort.save(policy);
    }
}
