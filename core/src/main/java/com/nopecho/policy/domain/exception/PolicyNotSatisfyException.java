package com.nopecho.policy.domain.exception;

public class PolicyNotSatisfyException extends RuntimeException {
    public PolicyNotSatisfyException(String message) {
        super(message);
    }
}
