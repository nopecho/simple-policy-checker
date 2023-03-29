package com.nopecho.policy.applications.usecases;

import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.applications.usecases.models.Response;

public interface PolicyApplyUseCase {
    Response.PolicyApplyResult apply(Request.FactorModel factorModel);
}
