package com.nopecho.policy.domain;

import com.nopecho.policy.domain.utils.OperatorMapper;
import com.nopecho.utils.Throw;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("DYNAMIC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecDynamicActualDetail extends SpecActualDetail {

    @Embedded
    private RequestTemplate template;

    private String tempResult;

    public static SpecDynamicActualDetail of(OperatorType type, RequestTemplate template) {
        throwIfInvalidArgs(type, template);
        return new SpecDynamicActualDetail(type, template);
    }

    private SpecDynamicActualDetail(OperatorType type, RequestTemplate template) {
        super(null, SpecActualDetailType.DYNAMIC, type);
        this.template = template;
    }

    private static void throwIfInvalidArgs(OperatorType type, RequestTemplate template) {
        Throw.ifNull(type, "Operator Type");
        Throw.ifNull(template, "Spec Actual template");
        if(RequestType.HTTP.equals(template.getRequestType())) {
            Throw.ifInvalidUrl(template.getUrl());
        }
    }

    public void setTempResult(String result) {
        this.tempResult = result;
    }

    public String getOrThrowTempResult() {
        if(this.tempResult.isBlank() || this.tempResult == null) {
            throw new IllegalStateException("결과를 먼저 할당 해야합니다.");
        }
        return this.tempResult;
    }

    @Override
    public <T> T getActualValue(Class<T> classOfT) {
        try {
            Object toObject = OperatorMapper.convertToObject(super.getOperatorType(), this.getOrThrowTempResult());
            return classOfT.cast(toObject);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("[%s] > [%s] 변환 실패::message:%s", this.tempResult, classOfT.getSimpleName(), e.getMessage()));
        }
    }
}
