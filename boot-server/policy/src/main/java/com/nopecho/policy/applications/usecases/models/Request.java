package com.nopecho.policy.applications.usecases.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface Request {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class FactorModel {
        @NotEmpty
        Long policyId;

        @NotNull
        Object factor;
    }
}
