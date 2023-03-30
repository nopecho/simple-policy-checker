package com.nopecho.policy.adapters.in;

import com.nopecho.policy.applications.usecases.PolicyApplyUseCase;
import com.nopecho.policy.applications.usecases.PolicyCreateUseCase;
import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.applications.usecases.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class CommandController {

    private final PolicyApplyUseCase applyUseCase;
    private final PolicyCreateUseCase createUseCase;

    @PostMapping("/policy/apply")
    public ResponseEntity<?> apply(@Valid @RequestBody Request.FactorModel requestedFactor) {
        Response.PolicyResult result = applyUseCase.apply(requestedFactor);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/policy")
    public ResponseEntity<?> apply(@Valid @RequestBody Request.PolicyModel requestedPolicy) {
        requestedPolicy.validate();
        Long policyId = createUseCase.create(requestedPolicy);

        Response.PolicyResult response = new Response.PolicyResult(policyId, true);
        return ResponseEntity.ok(response);
    }
}
