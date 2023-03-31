package com.nopecho.policy.applications.ports.in;

import com.nopecho.policy.applications.ports.in.models.Request;

public interface PolicyCreateUseCase {

    Long create(Request.PolicyModel policyModel);
}
