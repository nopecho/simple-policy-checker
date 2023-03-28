package com.nopecho.policy.applications.usecases;

import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.domain.Factor;

public interface PolicyApplyUseCase {
    void apply(Request.FactorModel factorModel);
}
