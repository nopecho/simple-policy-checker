package com.nopecho.policy.applications.services;

import com.nopecho.policy.applications.ports.out.*;
import com.nopecho.policy.applications.services.generators.FactorGenerator;
import com.nopecho.policy.applications.usecases.PolicyApplyUseCase;
import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyApplyService implements PolicyApplyUseCase {

    private final PolicyLoadPort loadPort;
    private final SpecVariablePort specVariablePort;
    private final ActionPerformPort actionPort;

    @Override
    public void apply(Request.FactorModel factorModel) {
        Policy policy = loadPort.loadById(factorModel.getPolicyId());
        Factor factor = FactorGenerator.gen(factorModel);
        Set<String> supportedVariable = policy.getSupportVariable(factor);

        resolveVariableSpecs(policy, factor, supportedVariable);

        Set<Action> actions = policy.apply(factor);
        actions.forEach(action -> actionPort.perform(action, factor, supportedVariable));

        resetSpecs(policy, factor);
    }

    private void resolveVariableSpecs(Policy policy, Factor factor, Set<String> supportedVariable) {
        policy.getSpecsFromSupported(factor)
                .forEach(spec -> specVariablePort.resolve(spec, factor, supportedVariable));
    }

    private void resetSpecs(Policy policy, Factor factor) {
        policy.getSpecsFromSupported(factor)
                .forEach(spec -> spec.getActual().resetResult());
    }
}
