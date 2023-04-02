package com.nopecho.policy.action.applications.ports.in.models;

import com.nopecho.policy.domain.ActionHistory;
import com.nopecho.policy.domain.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface Response {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class RetryResult {
        Long historyId;
        boolean result;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ActionHistoryModel {

        @NotNull
        Long id;

        Long actionId;

        Object factor;

        String status;

        Response.TemplateModel template;

        LocalDateTime occurredAt;

        int threshold;

        long retryCount;

        public static ActionHistoryModel toModel(ActionHistory history) {
            return new ActionHistoryModel(
                    history.getId(),
                    history.getActionId(),
                    history.getFactor(),
                    history.getStatus().name(),
                    TemplateModel.toModel(history.getTemplate()),
                    history.getOccurredAt().toLocalDateTime(),
                    history.getThreshold(),
                    history.getVersion());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class TemplateModel {
        @NotNull
        @NotBlank
        String method;

        @NotNull
        @NotBlank
        String url;

        @NotNull
        @NotBlank
        String body;

        String accessField;

        public static TemplateModel toModel(RequestTemplate template) {
            return new TemplateModel(
                    template.getMethod().name(), template.getUrl(), template.getBody(), template.getAccessField()
            );
        }
    }
}
