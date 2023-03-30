package com.nopecho.policy.applications.ports.out;


import com.nopecho.policy.domain.Factor;
import com.nopecho.policy.domain.Spec;

import java.util.Set;

public interface SpecVariableResolvePort {

    void resolve(Spec spec, Factor factor, Set<String> variables);
}
