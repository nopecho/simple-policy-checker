package com.nopecho.policy.domain;

import com.nopecho.policy.domain.exception.PolicyException;
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

    public Set<Spec> getSpecsFromSupported(Factor factor) {
        return findSupportedStatement(factor).stream()
                .map(Statement::getSpecs)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<Action> apply(Factor factor) {
        Set<Action> actions = findSupportedStatement(factor).stream()
                .filter(Statement::conditionCheck)
                .map(Statement::getActions)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        if(actions.isEmpty()) {
            throw new PolicyException(String.format("ID:[%s] NAME:[%s] 정책을 만족하지 않습니다.", this.policyId, this.name));
        }
        return actions;
    }

    public Set<String> getSupportVariable(Factor factor) {
        return findSupportedStatement(factor).stream()
                .map(Statement::getSupportVariables)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
        statement.setPolicy(this);
    }

    private Set<Statement> findSupportedStatement(Factor factor) {
        return this.statements.stream()
                .filter(statement -> statement.isSupport(factor))
                .collect(Collectors.toSet());
    }

    private static void throwIfInvalidArgs(String name) {
        Throw.ifNullOrBlank(name,"Policy Name");
    }
}
