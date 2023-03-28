package com.nopecho.policy.applications.ports.out;

public interface VariableResolvePort {

    <T> void resolveFor(T t);
}
