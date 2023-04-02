package com.nopecho.policy.action.adapters.in;

import com.nopecho.policy.action.applications.ports.in.ActionRetryUseCase;
import com.nopecho.policy.action.applications.ports.in.models.Request;
import com.nopecho.policy.action.applications.ports.in.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class HistoryCommandController {

    private final ActionRetryUseCase retryUseCase;

    @PostMapping("/policies/actions/histories/retry")
    public ResponseEntity<?> retry(@RequestBody Request.HistoryRetryModel requested) {
        Response.RetryResult result = retryUseCase.retryById(requested.getHistoryId());

        return ResponseEntity.ok(result);
    }
}
