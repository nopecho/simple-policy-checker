package com.nopecho.policy.adapters.in;

import com.nopecho.policy.applications.ports.in.PolicyApplyUseCase;
import com.nopecho.policy.applications.ports.in.PolicyCreateUseCase;
import com.nopecho.policy.applications.ports.in.PolicyUpdateUseCase;
import com.nopecho.policy.applications.ports.in.models.Request;
import com.nopecho.policy.applications.ports.in.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class PolicyCommandController {

    private final PolicyApplyUseCase applyUseCase;
    private final PolicyCreateUseCase createUseCase;
    private final PolicyUpdateUseCase updateUseCase;

    @PostMapping("/policies/apply")
    public ResponseEntity<?> apply(@Valid @RequestBody Request.FactorModel requestedFactor) {
        Response.PolicyResult result = applyUseCase.apply(requestedFactor);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/policies")
    public ResponseEntity<?> create(@Valid @RequestBody Request.PolicyModel requestedPolicy) {
        requestedPolicy.validate();
        Long policyId = createUseCase.create(requestedPolicy);

        Response.PolicyResult response = new Response.PolicyResult(policyId, true);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/policies/{policyId}")
    public ResponseEntity<?> update(@PathVariable Long policyId,
                                    @Valid @RequestBody Request.PolicyModel requestedPolicy) {
        requestedPolicy.validate();

        Response.PolicyModel response = updateUseCase.update(policyId, requestedPolicy);

        return ResponseEntity.ok(response);
    }
}
