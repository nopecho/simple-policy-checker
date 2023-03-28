package com.nopecho.policy.adapters.in;

import com.nopecho.policy.applications.usecases.PolicyApplyUseCase;
import com.nopecho.policy.applications.usecases.models.Request;
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

    @PostMapping("/policy/apply")
    public ResponseEntity<?> apply(@Valid @RequestBody Request.FactorModel model) {
        applyUseCase.apply(model);

        return ResponseEntity.ok(true);
    }
}
