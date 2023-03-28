package com.nopecho.policy.applications.ports.out;

import com.nopecho.policy.domain.Policy;

import java.util.List;

public interface PolicyLoadPort {

    Policy loadById(Long id);
}
