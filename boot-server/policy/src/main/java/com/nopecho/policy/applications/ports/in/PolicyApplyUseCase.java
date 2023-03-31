package com.nopecho.policy.applications.ports.in;

import com.nopecho.policy.applications.ports.in.models.Request;
import com.nopecho.policy.applications.ports.in.models.Response;

public interface PolicyApplyUseCase {
    Response.PolicyResult apply(Request.FactorModel factorModel);
}
