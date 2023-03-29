package com.nopecho.policy.applications.usecases;

import com.nopecho.policy.applications.usecases.models.Request;

public interface PolicyCreateUseCase {

    Long create(Request.PolicyModel policyModel);
}
