package com.nopecho.policy.adapters.in;

import com.nopecho.policy.applications.ports.in.PolicyQueryUseCase;
import com.nopecho.policy.applications.ports.in.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class PolicyQueryController {

    private final PolicyQueryUseCase queryUseCase;

    @GetMapping("/policies")
    public ResponseEntity<?> getAll(Pageable pageable) {
        Slice<Response.PolicyModel> policies = queryUseCase.queryAll(pageable);

        return ResponseEntity.ok(policies);
    }

    @GetMapping("/policies/{policyId}")
    public ResponseEntity<?> getById(@PathVariable Long policyId) {
        Response.PolicyModel policyModel = queryUseCase.queryById(policyId);

        return ResponseEntity.ok(policyModel);
    }

}
