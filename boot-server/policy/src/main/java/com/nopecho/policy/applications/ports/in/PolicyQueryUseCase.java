package com.nopecho.policy.applications.ports.in;

import com.nopecho.policy.applications.ports.in.models.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PolicyQueryUseCase {

    Slice<Response.PolicyModel> queryAll(Pageable pageable);

    Response.PolicyModel queryById(Long id);
}
