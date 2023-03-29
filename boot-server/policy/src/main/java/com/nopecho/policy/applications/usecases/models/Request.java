package com.nopecho.policy.applications.usecases.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public interface Request {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class FactorModel {
        @NotNull
        Long policyId;

        @NotNull
        Object factor;
    }
}
