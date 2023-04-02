package com.nopecho.policy.action.applications.services;

import com.nopecho.policy.action.applications.ports.in.ActionRetryUseCase;
import com.nopecho.policy.action.applications.ports.in.models.Response;
import com.nopecho.policy.action.applications.ports.out.ActionHistoryLoadPort;
import com.nopecho.policy.action.applications.ports.out.ActionHistoryPerformPort;
import com.nopecho.policy.action.applications.ports.out.NotificationPublishPort;
import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.HistoryStatus;
import com.nopecho.policy.domain.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class ActionRetryService implements ActionRetryUseCase {

    private final ActionHistoryLoadPort loadPort;
    private final ActionHistoryPerformPort performPort;
    private final NotificationPublishPort notificationPort;

    @Override
    public void retryAll() {
        List<ActionHistory> failedHistory = loadPort.loadByStatus(HistoryStatus.FAIL);

        notificationForThresholdHistory(failedHistory);

        failedHistory.stream()
                .filter(ActionHistory::isNotReachThreshold)
                .forEach(performPort::perform);
    }

    @Override
    public Response.RetryResult retryById(Long id) {
        ActionHistory actionHistory = loadPort.loadById(id);

        boolean result = performPort.perform(actionHistory);

        return new Response.RetryResult(actionHistory.getId(), result);
    }

    private void notificationForThresholdHistory(List<ActionHistory> failedHistory) {
        failedHistory.stream()
                .filter(ActionHistory::isReachThreshold)
                .map(this::createNotificationBody)
                .forEach(notificationPort::publish);
    }

    private NotificationPublishPort.Payload createNotificationBody(ActionHistory history) {
        RequestTemplate template = history.getTemplate();
        String message = String.format("ActionHistoryId: %s\n[%s %s] 액션 요청 [%s]회 실패",
                history.getId(), template.getMethod().name(), template.getUrl(), history.getThreshold());
        return new NotificationPublishPort.Payload("/api", "to", "title", message);
    }
}
