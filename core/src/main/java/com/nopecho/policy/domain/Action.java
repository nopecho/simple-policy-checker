package com.nopecho.policy.domain;

import com.nopecho.utils.Throw;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "policy_statement_action")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Action extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Embedded
    private RequestTemplate template;

    @ManyToOne
    @JoinColumn(name = "statement_id")
    private Statement statement;

    public static Action ofWebHook(String name, RequestTemplate template) {
        Throw.ifNull(template, "RequestTemplate");
        return new Action(null, Objects.requireNonNull(name, ""), ActionType.WEBHOOK, template, null);
    }

    public static Action ofAllowed(String name) {
        return new Action(null, Objects.requireNonNull(name, ""), ActionType.AllOWED, RequestTemplate.none(RequestOwner.ACTION), null);
    }

    public static Action ofDeny(String name) {
        return new Action(null, Objects.requireNonNull(name, ""), ActionType.DENY, RequestTemplate.none(RequestOwner.ACTION), null);
    }

    public boolean isWebHook() {
        return ActionType.WEBHOOK.equals(this.actionType);
    }

    public boolean isAllowed() {
        return ActionType.AllOWED.equals(this.actionType);
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
