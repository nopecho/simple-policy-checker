package com.nopecho.policy.domain;

import com.nopecho.policy.domain.utils.OperatorMapper;
import com.nopecho.utils.Throw;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("STATIC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecStaticActualDetail extends SpecActualDetail {

    private String actualValue;

    public static SpecStaticActualDetail of(OperatorType type, String value) {
        Throw.ifNull(type, "SpecStaticActualDetail type");
        Throw.ifNullOrBlank(value, "SpecStaticActualDetail value");
        return new SpecStaticActualDetail(type, value);
    }

    private SpecStaticActualDetail(OperatorType type, String actualValue) {
        super(null, SpecActualDetailType.STATIC, type);
        this.actualValue = actualValue;
    }

    @Override
    public <T> T getActualValue(Class<T> classOfT) {
        try {
            Object toObject = OperatorMapper.convertToObject(super.getOperatorType(), this.actualValue);
            return classOfT.cast(toObject);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("[%s] > [%s] 변환 실패", this.actualValue, classOfT.getSimpleName()));
        }
    }
}
