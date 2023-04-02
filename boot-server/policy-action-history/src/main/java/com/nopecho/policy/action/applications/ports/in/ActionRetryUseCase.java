package com.nopecho.policy.action.applications.ports.in;

import com.nopecho.policy.action.applications.ports.in.models.Response;

public interface ActionRetryUseCase {

    void retryAll();

    Response.RetryResult retryById(Long id);
}
