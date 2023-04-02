package com.nopecho.policy.action.applications.ports.in;

import com.nopecho.policy.action.applications.ports.in.models.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ActionHistoryQueryUseCase {

    Slice<Response.ActionHistoryModel> queryAllByPageable(Pageable pageable);
}
