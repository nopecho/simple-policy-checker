package com.nopecho.policy.action.applications.services;

import com.nopecho.policy.action.applications.ports.in.ActionHistoryQueryUseCase;
import com.nopecho.policy.action.applications.ports.in.models.Response;
import com.nopecho.policy.action.applications.ports.out.ActionHistoryLoadPort;
import com.nopecho.policy.domain.ActionHistory;
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
public class HistoryQueryService implements ActionHistoryQueryUseCase {

    private final ActionHistoryLoadPort loadPort;

    @Override
    public Slice<Response.ActionHistoryModel> queryAllByPageable(Pageable pageable) {
        Slice<ActionHistory> actionHistories = loadPort.loadAllByPageable(pageable);

        List<Response.ActionHistoryModel> models = actionHistories.stream()
                .map(Response.ActionHistoryModel::toModel)
                .toList();
        return new SliceImpl<>(models, actionHistories.getPageable(), actionHistories.hasNext());
    }
}
