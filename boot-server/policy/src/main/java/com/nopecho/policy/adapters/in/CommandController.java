package com.nopecho.policy.adapters.in;

import com.nopecho.policy.applications.usecases.PolicyApplyUseCase;
import com.nopecho.policy.applications.usecases.PolicyCreateUseCase;
import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.applications.usecases.models.Response;
import com.nopecho.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class CommandController {

    private final PolicyApplyUseCase applyUseCase;
    private final PolicyCreateUseCase createUseCase;

    @PostMapping("/policy/apply")
    public ResponseEntity<?> apply(@Valid @RequestBody Request.FactorModel model) {
        Response.PolicyApplyResult result = applyUseCase.apply(model);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/policy")
    public ResponseEntity<?> apply(@Valid @RequestBody Request.PolicyModel model) {
        model.validate();
        createUseCase.create(model);

        return ResponseEntity.ok(true);
    }
}
