package com.nopecho.policy.applications.services;

import com.nopecho.policy.applications.ports.out.*;
import com.nopecho.policy.applications.services.factors.FactorGenerator;
import com.nopecho.policy.applications.usecases.PolicyApplyUseCase;
import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.applications.usecases.models.Response;
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

    private final PolicyLoadPort policyLoadPort;
    private final SpecVariableResolvePort specVariablePort;
    private final ActionPerformPort actionPort;

    @Override
    public Response.PolicyResult apply(Request.FactorModel factorModel) {
        Factor factor = FactorGenerator.gen(factorModel);
        Policy policy = policyLoadPort.loadById(factorModel.getPolicyId());

        Set<String> supportedVariables = policy.getSupportVariable(factor);
        Set<Spec> supportedSpecs = policy.getSupportedSpecs(factor);
        resolveSpecsVariable(supportedSpecs, factor, supportedVariables);

        Set<Action> actions = policy.apply(factor);
        actions.forEach(action -> actionPort.perform(action, factor, supportedVariables));

        resetSpecs(supportedSpecs);
        return new Response.PolicyResult(policy.getPolicyId(), true);
    }

    private void resolveSpecsVariable(Set<Spec> specs, Factor factor, Set<String> variables) {
        specs.forEach(spec -> specVariablePort.resolve(spec, factor, variables));
    }

    private void resetSpecs(Set<Spec> specs) {
        specs.forEach(spec -> spec.getActual().resetResult());
    }
}
