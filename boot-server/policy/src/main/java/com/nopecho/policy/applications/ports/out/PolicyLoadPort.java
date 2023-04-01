package com.nopecho.policy.applications.ports.out;

import com.nopecho.policy.domain.Policy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PolicyLoadPort {

    Policy loadById(Long id);

    Slice<Policy> loadAllByPageable(Pageable pageable);
}
