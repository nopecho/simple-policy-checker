package com.nopecho.policy.adapters.out;


import com.nopecho.policy.applications.ports.out.ActionPerformPort;
import com.nopecho.policy.applications.ports.out.VariableResolvePort;
import com.nopecho.policy.domain.Action;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalWebAdapter implements VariableResolvePort, ActionPerformPort {

    @Override
    public void perform(Action action) {

    }

    @Override
    public <T> void resolveFor(T t) {

    }
}
