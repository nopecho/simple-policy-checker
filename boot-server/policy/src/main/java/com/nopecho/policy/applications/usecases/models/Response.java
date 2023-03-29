package com.nopecho.policy.applications.usecases.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface Response {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class PolicyResult {
        Long policyId;
        Boolean result;
    }
}
