package com.nopecho.policy.domain;

import com.nopecho.policy.domain.converter.OperatorAttributeConverter;
import com.nopecho.policy.domain.factor.Factor;
import com.nopecho.utils.Throw;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "policy_condition_spec")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Spec extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SpecActual actual;

    @Convert(converter = OperatorAttributeConverter.class)
    private Operator operator;

    @Embedded
    private SpecExpect expect;

    @ManyToOne
    @JoinColumn(name = "condition_id")
    private Condition condition;

    public static Spec of(SpecActual actual, Operator operator, SpecExpect expect) {
        throwIfInvalidArgs(actual, operator, expect);
        return new Spec(null, actual, operator, expect, null);
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    private static void throwIfInvalidArgs(SpecActual actual, Operator operator, SpecExpect expect) {
        Throw.ifNull(actual, "Condition Spec Actual");
        Throw.ifNull(operator, "Condition Spec Operator");
        Throw.ifNull(expect, "Condition Spec Expectation");
    }

    public boolean isSatisfy() {
        SpecActualDetail detail = this.actual.getActualDetail();
        SpecExpect expect = this.expect;
        this.typeValidation(detail.getOperatorType(), expect.getType());

        if(SpecActualDetailType.STATIC.equals(detail.getDetailType())) {
            SpecStaticActualDetail staticActual = this.castStatic(detail);
            return this.operator.isSatisfy().test(staticActual, expect);
        }

        SpecDynamicActualDetail dynamicActual = castDynamic(detail);
        return this.operator.isSatisfy().test(dynamicActual, expect);
    }

    private SpecDynamicActualDetail castDynamic(SpecActualDetail detail) {
        return (SpecDynamicActualDetail) detail;
    }

    private SpecStaticActualDetail castStatic(SpecActualDetail detail) {
        return (SpecStaticActualDetail) detail;
    }

    private void typeValidation(OperatorType type1, OperatorType type2) {
        if(!type1.equals(type2)) {
            throw new IllegalStateException(String.format("[%s] != [%s] 같은 타입만 연산이 가능합니다.", type1.name(), type2.name()));
        }
    }
}