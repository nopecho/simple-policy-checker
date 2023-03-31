package com.nopecho.policy.applications.services;

import com.nopecho.policy.applications.ports.in.models.Request;
import com.nopecho.policy.domain.Policy;

public class PolicyFactory {

    public static Policy create(Request.PolicyModel model) {
        return model.toEntity();
    }
}
