package com.nopecho.policy.applications.usecases;

import com.nopecho.policy.applications.usecases.models.Request;

public interface PolicyApplyUseCase {
    void apply(Request.FactorModel factorModel);
}
