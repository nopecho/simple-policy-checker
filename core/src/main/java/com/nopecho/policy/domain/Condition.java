package com.nopecho.policy.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "policy_statement_condition")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Condition extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "condition", cascade = CascadeType.ALL)
    private Set<Spec> specs;

    @ManyToOne
    @JoinColumn(name = "statement_id")
    private Statement statement;

    public static Condition of(String name, String description) {
        String desc = Objects.requireNonNull(description, "NONE");
        return new Condition(null, name, desc, new HashSet<>(), null);
    }

    public boolean isSatisfy() {
        return this.specs.stream()
                .allMatch(Spec::isSatisfy);
    }

    public void addSpec(Spec spec) {
        this.specs.add(spec);
        spec.setCondition(this);
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
