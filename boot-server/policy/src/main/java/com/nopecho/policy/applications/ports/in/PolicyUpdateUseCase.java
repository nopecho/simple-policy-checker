package com.nopecho.policy.applications.ports.in;

import com.nopecho.policy.applications.ports.in.models.Request;
import com.nopecho.policy.applications.ports.in.models.Response;

public interface PolicyUpdateUseCase {

    Response.PolicyModel update(Long id, Request.PolicyModel policyModel);
}
