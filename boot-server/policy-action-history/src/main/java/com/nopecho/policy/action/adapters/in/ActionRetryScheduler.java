package com.nopecho.policy.action.adapters.in;

import com.nopecho.policy.action.applications.ports.in.ActionRetryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@EnableScheduling
@Component
@RequiredArgsConstructor
public class ActionRetryScheduler {

    private final ActionRetryUseCase retryUseCase;

    @Async
    @Scheduled(cron = "0 0/5 * * * ?") // 5분 간격
    public void retry() {
        retryUseCase.retry();
    }
}
