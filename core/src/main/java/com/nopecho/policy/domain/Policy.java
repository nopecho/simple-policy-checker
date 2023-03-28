package com.nopecho.policy.domain;

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

    public static Policy of(Long id, String name, String description) {
        throwIfInvalidArgs(id, name);
        return new Policy(id, name, description, new HashSet<>());
    }

    public Set<Spec> getSpecsFromSupported(Factor factor) {
        return findSupportedStatement(factor).stream()
                .map(Statement::getSpecs)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<Action> apply(Factor factor) {
        return findSupportedStatement(factor).stream()
                .filter(Statement::conditionCheck)
                .map(Statement::getActions)
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

    private static void throwIfInvalidArgs(Long id, String name) {
        Throw.ifNull(id, "Policy ID");
        Throw.ifNullOrBlank(name,"Policy Name");
    }
}
