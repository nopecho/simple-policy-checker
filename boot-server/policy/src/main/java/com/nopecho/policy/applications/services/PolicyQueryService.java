package com.nopecho.policy.applications.services;

import com.nopecho.policy.applications.ports.in.PolicyQueryUseCase;
import com.nopecho.policy.applications.ports.in.models.Response;
import com.nopecho.policy.applications.ports.out.PolicyLoadPort;
import com.nopecho.policy.domain.Policy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyQueryService implements PolicyQueryUseCase {

    private final PolicyLoadPort loadPort;

    @Override
    public Slice<Response.PolicyModel> queryAll(Pageable pageable) {
        Slice<Policy> policies = loadPort.loadAllByPageable(pageable);

        List<Response.PolicyModel> policyModels = policies.stream()
                .map(Response.PolicyModel::toModel)
                .collect(Collectors.toList());
        return new SliceImpl<>(policyModels, policies.getPageable(), policies.hasNext());
    }

    @Override
    public Response.PolicyModel queryById(Long id) {
        Policy policy = loadPort.loadById(id);
        return Response.PolicyModel.toModel(policy);
    }
}
