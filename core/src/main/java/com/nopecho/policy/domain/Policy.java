package com.nopecho.policy.domain;

import com.nopecho.policy.domain.exception.PolicyNotSatisfyException;
import com.nopecho.policy.domain.factors.Factor;
import com.nopecho.utils.JsonUtils;
import com.nopecho.utils.Throw;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Policy extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long policyId;

    private String name;

    private String description;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private Set<Statement> statements;

    public static Policy of(String name, String description) {
        throwIfInvalidArgs(name);
        return new Policy(null, name, description, new HashSet<>());
    }

    public Set<Action> apply(Factor factor) {
        Set<Action> actions = findOrThrowSupportedStatement(factor).stream()
                .filter(Statement::conditionCheck)
                .map(Statement::getActions)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        if(actions.isEmpty()) {
            throw new PolicyNotSatisfyException(String.format("PolicyId:[%s] PolicyName:[%s] 정책을 만족하지 않습니다. Factor:%s", this.policyId, this.name, JsonUtils.get().toJson(factor)));
        }
        return actions;
    }

    public Set<Spec> getSupportSpecs(Factor factor) {
        return findOrThrowSupportedStatement(factor).stream()
                .map(Statement::getSpecs)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<String> getSupportFactorKeys(Factor factor) {
        return findOrThrowSupportedStatement(factor).stream()
                .map(Statement::getFactorKeys)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
        statement.setPolicy(this);
    }

    public void change(String name, String description, Set<Statement> statements) {
        this.name = name;
        this.description = description;
        statements.forEach(this::addStatement);
    }

    public void removeStatements() {
        this.statements.forEach(Statement::removePolicy);
        this.statements.clear();
    }

    private Set<Statement> findOrThrowSupportedStatement(Factor factor) {
        Set<Statement> supportedStatements = this.statements.stream()
                .filter(statement -> statement.isSupport(factor))
                .collect(Collectors.toSet());
        if(supportedStatements.isEmpty()) {
            String message = String.format("policyId:[%s], name:[%s] 에서 지원되는 구문이 없습니다. factor:[%s]",this.policyId, this.name, JsonUtils.get().toJson(factor));
            throw new IllegalArgumentException(message);
        }
        return supportedStatements;
    }

    private static void throwIfInvalidArgs(String name) {
        Throw.ifNullOrBlank(name,"Policy Name");
    }
}
