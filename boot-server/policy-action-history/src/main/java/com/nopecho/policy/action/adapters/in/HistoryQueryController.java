package com.nopecho.policy.action.adapters.in;

import com.nopecho.policy.action.applications.ports.in.ActionHistoryQueryUseCase;
import com.nopecho.policy.action.applications.ports.in.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class HistoryQueryController {

    private final ActionHistoryQueryUseCase queryUseCase;

    @GetMapping("/policies/actions/histories")
    public ResponseEntity<?> getAll(Pageable pageable) {
        Slice<Response.ActionHistoryModel> historyModels = queryUseCase.queryAllByPageable(pageable);

        return ResponseEntity.ok(historyModels);
    }
}
