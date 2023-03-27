package com.nopecho.policy.domain;

import com.nopecho.utils.Throw;
import lombok.*;

import javax.persistence.*;

@Table(name = "policy_statement_action")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Action {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Embedded
    private RequestTemplate template;

    @ManyToOne
    @JoinColumn(name = "statement_id")
    private Statement statement;

    public static Action ofWebHook(RequestTemplate template) {
        Throw.ifNull(template, "RequestTemplate");
        return new Action(null, ActionType.WEBHOOK, template, null);
    }

    public static Action ofAllowed() {
        return new Action(null, ActionType.AllOWED, RequestTemplate.none(), null);
    }

    public static Action ofDeny() {
        return new Action(null, ActionType.DENY, RequestTemplate.none(), null);
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
