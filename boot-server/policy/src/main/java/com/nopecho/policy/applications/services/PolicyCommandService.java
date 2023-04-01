package com.nopecho.policy.applications.services;

import com.nopecho.policy.applications.ports.in.PolicyUpdateUseCase;
import com.nopecho.policy.applications.ports.in.models.Response;
import com.nopecho.policy.applications.ports.out.PolicyLoadPort;
import com.nopecho.policy.applications.ports.out.PolicySavePort;
import com.nopecho.policy.applications.ports.in.PolicyCreateUseCase;
import com.nopecho.policy.applications.ports.in.models.Request;
import com.nopecho.policy.applications.services.generators.PolicyFactory;
import com.nopecho.policy.domain.Policy;
import com.nopecho.policy.domain.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyCommandService implements PolicyCreateUseCase, PolicyUpdateUseCase {

    private final PolicyLoadPort loadPort;
    private final PolicySavePort savePort;

    @Override
    public Long create(Request.PolicyModel policyModel) {
        Policy policy = PolicyFactory.create(policyModel);

        return savePort.save(policy);
    }

    @Override
    public Response.PolicyModel update(Long id, Request.PolicyModel policyModel) {
        Policy policy = loadPort.loadById(id);
        Set<Statement> statements = policyModel.toStatements();

        policy.removeStatements();
        policy.change(policyModel.getName(), policyModel.getDescription(), statements);

        return Response.PolicyModel.toModel(policy);
    }
}
