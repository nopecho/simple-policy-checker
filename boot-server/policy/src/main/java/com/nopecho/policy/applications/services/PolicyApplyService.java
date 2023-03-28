package com.nopecho.policy.applications.services;

import com.nopecho.policy.applications.ports.out.ActionPerformPort;
import com.nopecho.policy.applications.ports.out.PolicyLoadPort;
import com.nopecho.policy.applications.ports.out.VariableResolvePort;
import com.nopecho.policy.applications.services.generators.FactorGenerator;
import com.nopecho.policy.applications.usecases.PolicyApplyUseCase;
import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyApplyService implements PolicyApplyUseCase {

    private final PolicyLoadPort loadPort;
    private final VariableResolvePort variablePort;
    private final ActionPerformPort actionPort;

    @Override
    public void apply(Request.FactorModel factorModel) {
        Policy policy = loadPort.loadById(factorModel.getPolicyId());
        Factor factor = FactorGenerator.gen(factorModel);
        resolveVariableSpecs(policy, factor);

        Set<Action> actions = policy.apply(factor);
        actions.stream()
                .peek(variablePort::resolveFor)
                .forEach(actionPort::perform);

        resetVariableSpecs(policy, factor);
    }

    private void resolveVariableSpecs(Policy policy, Factor factor) {
        policy.getSpecsFromSupported(factor)
                .forEach(variablePort::resolveFor);
    }

    private void resetVariableSpecs(Policy policy, Factor factor) {
        policy.getSpecsFromSupported(factor)
                .forEach(spec -> spec.getActual().resetResult());
    }
}
