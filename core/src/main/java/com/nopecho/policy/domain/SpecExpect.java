package com.nopecho.policy.domain;

import com.nopecho.policy.domain.utils.OperatorMapper;
import com.nopecho.utils.Throw;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecExpect {

    @Column(name = "expect_operator_type")
    @Enumerated(EnumType.STRING)
    private OperatorType type;
    private String expectValue;

    public static SpecExpect of(OperatorType type, String value) {
        Throw.ifNull(type, "Spec Expect Operator Type");
        Throw.ifNullOrBlank(value, "Spec Expect Value");
        return new SpecExpect(type, value);
    }

    public <T> T getExpectValue(Class<T> classOfT) {
        try {
            Object toObject = OperatorMapper.convertToObject(this.type, this.expectValue);
            return classOfT.cast(toObject);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("[%s] > [%s] 변환 실패", this.expectValue, classOfT.getSimpleName()));
        }
    }
}
