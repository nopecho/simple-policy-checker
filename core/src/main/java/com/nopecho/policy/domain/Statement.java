package com.nopecho.policy.domain;

import com.nopecho.utils.JsonUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Table(name = "policy_statement")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Statement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private Set<String> factorKeys;

    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL)
    private Set<Action> actions;

    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL)
    private Set<Condition> conditions;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    public static Statement of(Set<String> supportVariables) {
        return new Statement(null, supportVariables, new HashSet<>(), new HashSet<>(), null);
    }

    public boolean conditionCheck () {
        return this.conditions.stream()
                .allMatch(Condition::isSatisfy);
    }

    public boolean isSupport(Factor factor) {
        return this.factorKeys.stream()
                .allMatch(key -> JsonUtils.isExistPropertyKeyFrom(factor, key));
    }

    public Set<Spec> getSpecs() {
        return this.conditions.stream()
                .map(Condition::getSpecs)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
        condition.setStatement(this);
    }

    public void addAction(Action action) {
        this.actions.add(action);
        action.setStatement(this);
    }
}
