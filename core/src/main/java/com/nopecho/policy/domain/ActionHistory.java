package com.nopecho.policy.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Table(name = "policy_action_history")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long actionId;

    private String factor;

    @Enumerated(EnumType.STRING)
    private HistoryStatus status;

    @Embedded
    private RequestTemplate template;

    private ZonedDateTime occurredAt;

    @Version
    private long version;

    public static ActionHistory of(Long actionId, String factor, RequestTemplate template) {
        return new ActionHistory(null, actionId, factor, HistoryStatus.PENDING, template, null, 0);
    }

    public void failFrom(ZonedDateTime occurredAt) {
        this.status = HistoryStatus.FAIL;
        this.occurredAt = occurredAt;
    }

    public void successFrom(ZonedDateTime occurredAt) {
        this.status = HistoryStatus.SUCCESS;
        this.occurredAt = occurredAt;
    }
}
