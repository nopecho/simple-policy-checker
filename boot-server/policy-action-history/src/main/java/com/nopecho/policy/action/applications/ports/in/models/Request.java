package com.nopecho.policy.action.applications.ports.in.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface Request {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class HistoryRetryModel {
        Long historyId;
    }
}
